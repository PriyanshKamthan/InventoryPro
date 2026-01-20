package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.SaleItemResponseDTO;
import com.kamthan.InventoryPro.dto.SaleResponseDTO;
import com.kamthan.InventoryPro.exception.InsufficientStockException;
import com.kamthan.InventoryPro.exception.InvalidRequestException;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.Sale;
import com.kamthan.InventoryPro.model.SaleItem;
import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.repository.ProductRepository;
import com.kamthan.InventoryPro.repository.SaleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockMovementService stockMovementService;

    Logger logger
            = LoggerFactory.getLogger(SaleService.class);

    // add sale method
    @Transactional
    public Sale addSale(Sale sale) {
        double totalAmount = 0.0;
        double totalTax = 0.0;

        // validate items
        if (sale.getItems() == null || sale.getItems().isEmpty()) {
            throw new IllegalArgumentException("Sale must contain at least one item");
        }

        // cache products to avoid multiple DB hits
        Map<Long, Product> productCache = new HashMap<>();

        // First pass: validate and prepare
        for (SaleItem item : sale.getItems()) {
            Long productId = item.getProduct() == null ? null : item.getProduct().getId();
            if (productId == null) {
                throw new ResourceNotFoundException("Product id is required for each sale item");
            }

            Product product = productCache.computeIfAbsent(productId,
                    id -> productRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id)));

            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new InsufficientStockException("Quantity must be > 0 for product id: " + productId);
            }

            int available = product.getQuantity() == null ? 0 : product.getQuantity();
            int qty = item.getQuantity();
            if (available < qty) {
                throw new InsufficientStockException("Insufficient stock for product id: " + productId +
                        ". Available: " + available + ", requested: " + qty);
            }

            // set pricePerUnit from product master if not provided
            if (item.getPricePerUnit() == null) {
                item.setPricePerUnit(product.getPrice());
            }

            // compute tax if not provided (default 10%)
            double tax = item.getTaxAmount() != null ? item.getTaxAmount()
                    : item.getPricePerUnit() * qty * 0.1;
            item.setTaxAmount(tax);

            totalTax += tax;
            totalAmount += item.getPricePerUnit() * qty + tax;

            // update product quantity in cache (decrement)
            product.setQuantity(available - qty);
            productCache.put(productId, product);

            // associate item with sale (so cascade persists items)
            item.setSale(sale);
        }

        sale.setTotalAmount(totalAmount);
        sale.setTaxAmount(totalTax);
        sale.setSaleDate(LocalDateTime.now());

        // Save sale (ensure cascade = ALL on items)
        Sale savedSale = saleRepository.save(sale);

        productRepository.saveAll(productCache.values());

        for (SaleItem item : savedSale.getItems()) {
            Long productId = item.getProduct().getId();
            Product product = productCache.get(productId);

            int after = product.getQuantity();
            int qty = item.getQuantity();
            int before = after + qty;

            stockMovementService.recordMovement(
                    product,
                    MovementType.OUT,
                    qty,
                    before,
                    after,
                    ReferenceType.SALE,
                    savedSale.getId()
            );
        }

        return savedSale;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public List<SaleResponseDTO> getSalesByDateRange(LocalDate from, LocalDate to) {
        if(from == null || to == null) {
            throw new InvalidRequestException("From date and To date are required");
        }
        if(from.isAfter(to)) {
            throw new InvalidRequestException("From date can not be after To date");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23,59,59);

        List<Sale> sales = saleRepository.findBySaleDateBetween(start, end);

        if(sales.isEmpty()) throw new ResourceNotFoundException("No sales found between "+from+" and "+to);

        return sales.stream()
                .map(this::mapToSaleResponseDTO)
                .toList();
    }

    public SaleResponseDTO mapToSaleResponseDTO(Sale sale) {
        SaleResponseDTO dto = new SaleResponseDTO();

        dto.setSaleId(sale.getId());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setTotalTax(sale.getTaxAmount());

        if(sale.getCustomer()!=null) dto.setCustomerName(sale.getCustomer().getName());

        List<SaleItemResponseDTO> items = sale.getItems().stream()
                .map(item-> {
                    SaleItemResponseDTO itemDTO = new SaleItemResponseDTO();
                    itemDTO.setProductId(item.getProduct().getId());
                    itemDTO.setProductName(item.getProduct().getName());
                    itemDTO.setQuantity(item.getQuantity());
                    itemDTO.setPricePerUnit(item.getPricePerUnit());
                    itemDTO.setTaxAmount(item.getTaxAmount());
                    return itemDTO;
                }).toList();

        dto.setItems(items);
        return dto;
    }
}

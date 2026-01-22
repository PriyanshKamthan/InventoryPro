package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.dto.SaleResponseDTO;
import com.kamthan.InventoryPro.exception.InsufficientStockException;
import com.kamthan.InventoryPro.exception.InvalidRequestException;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.SaleMapper;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.Sale;
import com.kamthan.InventoryPro.model.SaleItem;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.repository.ProductRepository;
import com.kamthan.InventoryPro.repository.SaleRepository;
import com.kamthan.InventoryPro.service.SaleService;
import com.kamthan.InventoryPro.service.StockMovementService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockMovementService stockMovementService;

    // add sale method
    @Override
    @Transactional
    public Sale addSale(Sale sale) {

        log.info("Sale initiated | customerId={} | itemsCount={}",
                sale.getCustomer() != null ? sale.getCustomer().getId() : null,
                sale.getItems() != null ? sale.getItems().size() : 0);

        double totalAmount = 0.0;
        double totalTax = 0.0;

        // validate items
        if (sale.getItems() == null || sale.getItems().isEmpty()) {
            log.error("Sale failed | no sale items provided");
            throw new InvalidRequestException("Sale must contain at least one item");
        }

        // cache products to avoid multiple DB hits
        Map<Long, Product> productCache = new HashMap<>();

        // First pass: validate and prepare
        for (SaleItem item : sale.getItems()) {
            Long productId = item.getProduct() == null ? null : item.getProduct().getId();
            if (productId == null) {
                log.error("Sale failed | missing product in sale item");
                throw new InvalidRequestException("Product id is required for each sale item");
            }

            Product product = productCache.computeIfAbsent(productId,
                    id -> productRepository.findById(id).orElseThrow(() -> {
                        log.warn("Sale failed | Product not found with id:{}", id);
                        return new ResourceNotFoundException("Product not found with id: " + id);
                    }));

            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                log.warn("Invalid sale quantity | productId={} | qty={}", productId, item.getQuantity());
                throw new InsufficientStockException("Quantity must be > 0 for product id: " + productId);
            }

            int available = product.getQuantity() == null ? 0 : product.getQuantity();
            int qty = item.getQuantity();
            if (available < qty) {
                log.error("Sale failed | Insufficient stock for product id: {} Available:{}, requested: {}"
                        , productId, available, qty);
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

        log.info("Sale calculated | totalAmount={} | totalTax={}", totalAmount, totalTax);

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

        log.info("Sale saved successfully | saleId={} | customerId={}",
                savedSale.getId(), savedSale.getCustomer() != null ? savedSale.getCustomer().getId() : null
        );
        return savedSale;
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {
        log.info("Fetching all sale");
        return saleRepository.findAll().stream()
                .map(saleMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<SaleResponseDTO> getSalesByDateRange(LocalDate from, LocalDate to) {
        log.info("Fetching sale from:{} to:{}", from, to);
        if (from == null || to == null) {
            log.warn("Sale: From date and To date are required");
            throw new InvalidRequestException("From date and To date are required");
        }
        if (from.isAfter(to)) {
            log.warn("Sale: From date cannot be after To date");
            throw new InvalidRequestException("From date can not be after To date");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        List<Sale> saleList = saleRepository.findBySaleDateBetween(start, end);

        if (saleList.isEmpty()) {
            log.warn("No sale found between from:{} and to:{}", from, to);
            throw new ResourceNotFoundException("No sale found between " + from + " and " + to);
        }

        log.info("Sale fetched successfully, size():{}",saleList.size());
        return saleList.stream()
                .map(saleMapper::toResponseDTO)
                .toList();
    }
}

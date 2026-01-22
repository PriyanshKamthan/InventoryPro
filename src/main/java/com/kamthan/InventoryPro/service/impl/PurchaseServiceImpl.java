package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.dto.PurchaseResponseDTO;
import com.kamthan.InventoryPro.exception.InvalidRequestException;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.PurchaseMapper;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.Purchase;
import com.kamthan.InventoryPro.model.PurchaseItem;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.repository.ProductRepository;
import com.kamthan.InventoryPro.repository.PurchaseRepository;
import com.kamthan.InventoryPro.service.PurchaseService;
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
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockMovementService stockMovementService;

    @Override
    @Transactional
    public Purchase addPurchase(Purchase purchase) {
        log.info("Purchase initiated | supplierId={} | itemsCount={}",
                purchase.getSupplier() != null ? purchase.getSupplier().getId() : null,
                purchase.getItems() != null ? purchase.getItems().size() : 0);

        double totalAmount = 0.0;
        double totalTax = 0.0;

        if (purchase.getItems() == null || purchase.getItems().isEmpty()) {
            log.error("Purchase failed | no purchase items provided");
            throw new InvalidRequestException("Purchase must contain at least one item");
        }

        Map<Long, Product> productCache = new HashMap<>();

        for (PurchaseItem item : purchase.getItems()) {
            Long productId = item.getProduct().getId();
            if (productId == null) {
                log.error("Purchase failed | Product id is required for each purchase item");
                throw new InvalidRequestException("Product id is required for each purchase item");
            }

            log.info(
                    "Processing purchase item | productId={} | qty={} | pricePerUnit={} | taxPerUnit={}",
                    item.getProduct().getId(), item.getQuantity(), item.getPricePerUnit(), item.getTaxAmount());


            Product product = productCache.computeIfAbsent(productId,
                    id -> productRepository.findById(id).orElseThrow(() -> {
                                log.warn("Product not found with id:{}",id);
                                return new ResourceNotFoundException("Product not found with id: " + id);
                            }));

            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                log.warn("Invalid purchase quantity | productId={} | qty={}", productId, item.getQuantity());
                throw new InvalidRequestException("Quantity must be > 0 for product id: " + productId);
            }

            int before = product.getQuantity() == null ? 0 : product.getQuantity();
            int qty = item.getQuantity();

            if (item.getPricePerUnit() == null) {
                item.setPricePerUnit(product.getPrice());
            }

            double tax = item.getTaxAmount() != null ? item.getTaxAmount()
                    : item.getPricePerUnit() * qty * 0.1;
            item.setTaxAmount(tax);

            totalTax += tax;
            totalAmount += item.getPricePerUnit() * qty + tax;

            product.setQuantity(before + qty);
            productCache.put(productId, product);

            item.setPurchase(purchase);
        }

        log.info("Purchase calculated | totalAmount={} | totalTax={}", totalAmount, totalTax);

        purchase.setTotalAmount(totalAmount);
        purchase.setTaxAmount(totalTax);
        purchase.setPurchaseDate(LocalDateTime.now());

        Purchase savedPurchase = purchaseRepository.save(purchase);

        productRepository.saveAll(productCache.values());

        for (PurchaseItem item : savedPurchase.getItems()) {
            Long productId = item.getProduct().getId();
            Product product = productCache.get(productId);

            int after = product.getQuantity();
            int qty = item.getQuantity();
            int before = after - qty;

            stockMovementService.recordMovement(
                    product,
                    MovementType.IN,
                    qty,
                    before,
                    after,
                    ReferenceType.PURCHASE,
                    savedPurchase.getId()
            );
        }

        log.info("Purchase saved successfully | purchaseId={} | supplierId={}",
                savedPurchase.getId(), savedPurchase.getSupplier() != null ? savedPurchase.getSupplier().getId() : null);

        return savedPurchase;
    }

    @Override
    public List<PurchaseResponseDTO> getAllPurchases() {
        log.info("Fetching all purchase");
        return purchaseRepository.findAll().stream()
                .map(purchaseMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<PurchaseResponseDTO> getPurchasesByDateRange(LocalDate from, LocalDate to) {
        log.info("Fetching purchase from:{} to:{}", from, to);
        if (from == null || to == null) {
            log.warn("Purchase: From date and To date are required");
            throw new InvalidRequestException("From date and To date are required");
        }

        if (from.isAfter(to)) {
            log.warn("Purchase: From date cannot be after To date");
            throw new InvalidRequestException("From date cannot be after To date");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        List<Purchase> purchaseList =
                purchaseRepository.findByPurchaseDateBetween(start, end);

        if (purchaseList.isEmpty()) {
            log.warn("No purchase found between from:{} and to:{}", from, to);
            throw new ResourceNotFoundException("No purchase found between " + from + " and " + to);
        }

        log.info("Purchase fetched successfully, size():{}",purchaseList.size());
        return purchaseList.stream()
                .map(purchaseMapper::toResponseDTO)
                .toList();
    }
}

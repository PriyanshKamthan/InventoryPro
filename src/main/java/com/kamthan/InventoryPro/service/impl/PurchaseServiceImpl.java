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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        double totalAmount = 0.0;
        double totalTax = 0.0;

        if (purchase.getItems() == null || purchase.getItems().isEmpty()) {
            throw new IllegalArgumentException("Purchase must contain at least one item");
        }

        Map<Long, Product> productCache = new HashMap<>();

        for (PurchaseItem item : purchase.getItems()) {
            Long productId = item.getProduct().getId();
            if (productId == null) {
                throw new IllegalArgumentException("Product id is required for each purchase item");
            }

            Product product = productCache.computeIfAbsent(productId,
                    id -> productRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id)));

            if (item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be > 0 for product id: " + productId);
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

        return savedPurchase;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    @Override
    public List<PurchaseResponseDTO> getPurchasesByDateRange(LocalDate from, LocalDate to) {
        if (from == null || to == null) {
            throw new InvalidRequestException("From date and To date are required");
        }

        if (from.isAfter(to)) {
            throw new InvalidRequestException("From date cannot be after To date");
        }

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end = to.atTime(23, 59, 59);

        List<Purchase> purchases =
                purchaseRepository.findByPurchaseDateBetween(start, end);

        if (purchases.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No purchases found between " + from + " and " + to
            );
        }

        return purchases.stream()
                .map(purchaseMapper::toResponseDTO)
                .toList();
    }
}

package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.PurchaseResponseDTO;
import com.kamthan.InventoryPro.model.Purchase;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseService {
    public Purchase addPurchase(Purchase purchase);

    public List<PurchaseResponseDTO> getAllPurchases();

    public List<PurchaseResponseDTO> getPurchasesByDateRange(LocalDate from, LocalDate to);

    @PreAuthorize("hasRole('ADMIN')")
    public void reversePurchase(Long purchaseId);
}

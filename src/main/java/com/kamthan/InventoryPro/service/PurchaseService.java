package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.PurchaseResponseDTO;
import com.kamthan.InventoryPro.model.Purchase;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseService {
    public Purchase addPurchase(Purchase purchase);

    public List<Purchase> getAllPurchases();

    public List<PurchaseResponseDTO> getPurchasesByDateRange(LocalDate from, LocalDate to);
}

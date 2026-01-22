package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.SaleResponseDTO;
import com.kamthan.InventoryPro.model.Sale;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface SaleService {
    @Transactional
    public Sale addSale(Sale sale);

    public List<SaleResponseDTO> getAllSales();

    public List<SaleResponseDTO> getSalesByDateRange(LocalDate from, LocalDate to);
}

package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.SaleResponseDTO;
import com.kamthan.InventoryPro.model.Sale;
import com.kamthan.InventoryPro.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @PostMapping
    public ApiResponse<SaleResponseDTO> addSale(@RequestBody Sale sale) {
        return new ApiResponse<>(
                true,
                "Sale added successfully",
                saleService.addSale(sale)
        );
    }

    @PostMapping("/{saleId}/reverse")
    public ApiResponse<String> reverseSale(@PathVariable("saleId") long saleId) {
        saleService.reverseSale(saleId);
        return new ApiResponse<>(true, "Sale got reversed", null);
    }

    @GetMapping
    public ApiResponse<List<SaleResponseDTO>> getAllSales() {
        return new ApiResponse<>(
                true,
                "Sales fetched successfully",
                saleService.getAllSales());
    }

    @GetMapping("/filter")
    public ApiResponse<List<SaleResponseDTO>> getSalesByDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate to) {
        return new ApiResponse<>(true, "Sales fetched successfully", saleService.getSalesByDateRange(from, to));
    }
}
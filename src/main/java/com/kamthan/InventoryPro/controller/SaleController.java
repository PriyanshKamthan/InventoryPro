package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.model.Sale;
import com.kamthan.InventoryPro.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @PostMapping
    public ApiResponse<Sale> addSale(@RequestBody Sale sale) {
        return new ApiResponse<>(
                true,
                "Sale added successfully",
                saleService.addSale(sale)
        );
    }

    @GetMapping
    public ApiResponse<List<Sale>> getAllSales() {
        return new ApiResponse<>(
                true,
                "Sales fetched successfully",
                saleService.getAllSales());
    }
}
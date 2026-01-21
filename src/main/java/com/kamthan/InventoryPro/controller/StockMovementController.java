package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.StockMovementResponseDTO;
import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock-movement")
public class StockMovementController {
    @Autowired
    private StockMovementService stockMovementService;

    @GetMapping("/product/{productId}")
    public ApiResponse<List<StockMovementResponseDTO>> getForProduct(@PathVariable("productId") Long productId) {
        return new ApiResponse<>(
                true,
                "Stock movement of product fetched successfully",
                stockMovementService.getMovementsForProduct(productId)
        );
    }
}

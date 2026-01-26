package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.dashboard.*;
import com.kamthan.InventoryPro.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/stock-summary")
    public ApiResponse<StockSummaryDTO> getStockSummary() {
        return new ApiResponse<>(true,
                "Stock summary fetched successfully",
                dashboardService.getStockSummary());
    }

    @GetMapping("/sale-summary")
    public ApiResponse<SalesSummaryDTO> getSalesSummary() {
        return new ApiResponse<>(true,
                "Sale summary fetched successfully",
                dashboardService.getSalesSummary());
    }

    @GetMapping("/purchase-summary")
    public ApiResponse<PurchaseSummaryDTO> getPurchaseSummary() {
        return new ApiResponse<>(true,
                "Purchase summary fetched successfully",
                dashboardService.getPurchaseSummary());
    }

    @GetMapping("/recent-stock-movement")
    public ApiResponse<List<RecentStockMovementDTO>> getRecentStockMovements(
            @RequestParam(defaultValue = "10") int limit
    ) {
        return new ApiResponse<>(true,
                "Recent stock movement fetched successfully",
                dashboardService.getRecentStockMovements(limit));
    }

    @GetMapping("/top-selling-products")
    public ApiResponse<List<TopSellingProductDTO>> getTopSellingProducts(
            @RequestParam(defaultValue = "5") int limit) {
        return new ApiResponse<>(true,
                "Top selling products fetched successfully",
                dashboardService.getTopSellingProductsForCurrentMonth(limit));
    }
}
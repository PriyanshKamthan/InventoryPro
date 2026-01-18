package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.ProductReportDTO;
import com.kamthan.InventoryPro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ProductService productService;
    @GetMapping("/stocks")
    public ApiResponse<Map<String, List<ProductReportDTO>>> getProductReport() {
        return new ApiResponse<>(
                true,
                "Category wise stock report generated",
                productService.getProductsGroupedByCategory()
        );
    }
}

package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.PurchaseResponseDTO;
import com.kamthan.InventoryPro.model.Purchase;
import com.kamthan.InventoryPro.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ApiResponse<Purchase> addPurchase(@RequestBody Purchase purchase) {
        return new ApiResponse<>(
                true,
                "Purchase added successfully",
                purchaseService.addPurchase(purchase)
        );
    }

    @GetMapping
    public ApiResponse<List<PurchaseResponseDTO>> getAllPurchases() {
        return new ApiResponse<>(
                true,
                "Purchases fetched successfully",
                purchaseService.getAllPurchases()
        );
    }

    @GetMapping("/filter")
    public ApiResponse<List<PurchaseResponseDTO>> getPurchasesByDateRange(
            @RequestParam("from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

            @RequestParam("to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return new ApiResponse<>(
                true,
                "Purchases fetched successfully",
                purchaseService.getPurchasesByDateRange(from, to));
    }

//    @GetMapping("/{supplierId}")
//    public List<Purchase> getPurchasesOfSupplier(@PathVariable Long supplierId){
//        return null;
//    }
}

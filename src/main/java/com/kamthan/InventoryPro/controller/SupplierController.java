package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.SupplierResponseDTO;
import com.kamthan.InventoryPro.model.Supplier;
import com.kamthan.InventoryPro.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @PostMapping
    public ApiResponse<SupplierResponseDTO> addSupplier(@RequestBody Supplier supplier) {
        return new ApiResponse<>(
                true,
                "Supplier added successfully",
                supplierService.addSupplier(supplier)
        );
    }

    @GetMapping
    public ApiResponse<List<SupplierResponseDTO>> getAllSuppliers() {
        return new ApiResponse<>(
                true,
                "Suppliers fetched successfully",
                supplierService.getAllSuppliers()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<SupplierResponseDTO> updateSupplier(@PathVariable("id") Long id, @RequestBody Supplier supplier) {
        return new ApiResponse<>(
                true,
                "Supplier updated successfully",
                supplierService.updateSupplier(id, supplier)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<SupplierResponseDTO> getSupplierById(@PathVariable("id") Long id) {
        return new ApiResponse<>(
                true,
                "Supplier details fetched successfully",
                supplierService.getSupplierById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSupplier(@PathVariable("id") Long id) {
        supplierService.deleteSupplier(id);
        return new ApiResponse<>(
                true,
                "Supplier deleted successfully",
                null
        );
    }

    @GetMapping("/search")
    public ApiResponse<List<SupplierResponseDTO>> searchSuppliers(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "gstNumber", required = false) String gstNumber) {
        return new ApiResponse<>(
                true,
                "Suppliers fetched successfully",
                supplierService.searchSuppliers(name, phone, email, gstNumber)
        );
    }
}

package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.ProductResponseDTO;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ApiResponse<ProductResponseDTO> addProduct(@RequestBody Product product) {
        return new ApiResponse<>(
                true,
                "Product added successfully",
                productService.addProduct(product)
        );
    }

    @GetMapping
    public ApiResponse<List<ProductResponseDTO>> getAllProducts() {
        //return productService.getAllProducts();
        return new ApiResponse<>(
                true,
                "All Products fetched successfully",
                productService.getAllProducts()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDTO> getProductById(@PathVariable("id") Long id) {
        return new ApiResponse<>(
                true,
                "Product details fetched successfully",
                productService.findProductById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDTO> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return new ApiResponse<>(
                true,
                "Product updated successfully",
                productService.updateProduct(id, product)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        );
    }

    @PutMapping("/{id}/restore")
    public ApiResponse<Void> restoreProduct(@PathVariable Long id) {
        productService.restoreProduct(id);
        return new ApiResponse<>(
                true,
                "Product restored successfully",
                null);
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponseDTO>> searchProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category) {
        return new ApiResponse<>(true, "Products fetched successfully",
                productService.searchProducts(name, category));
    }
}

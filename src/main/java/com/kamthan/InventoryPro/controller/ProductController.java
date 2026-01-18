package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.ProductSearchDTO;
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
    public ApiResponse<Product> addProduct(@RequestBody Product product) {
        return new ApiResponse<>(
                true,
                "Product added successfully",
                productService.addProduct(product)
        );
    }

    @GetMapping
    public ApiResponse<List<Product>> getAllProducts() {
        //return productService.getAllProducts();
        return new ApiResponse<>(
                true,
                "All Products fetched successfully",
                productService.getAllProducts()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Product> getProductById(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Product details fetched successfully",
                productService.findProductById(id)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return new ApiResponse<>(
                true,
                "Product updated successfully",
                productService.updateProduct(id, product)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ApiResponse<>(
                true,
                "Product deleted successfully",
                null
        );
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductSearchDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        return new ApiResponse<>(true, "Products fetched successfully",
                productService.searchProducts(name, category));
    }
}

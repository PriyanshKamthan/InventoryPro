package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.ProductReportDTO;
import com.kamthan.InventoryPro.dto.ProductSearchDTO;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.enums.UnitOfMeasure;
import com.kamthan.InventoryPro.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) {
        if (product.getUnitOfMeasure() == null) {
            product.setUnitOfMeasure(UnitOfMeasure.PIECE);
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found with id: "+id));
    }
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = findProductById(id);
        if (existing != null) {
            existing.setName(updatedProduct.getName());
            existing.setCategory(updatedProduct.getCategory());
            existing.setPrice(updatedProduct.getPrice());
            existing.setQuantity(updatedProduct.getQuantity());
            existing.setUnitOfMeasure(updatedProduct.getUnitOfMeasure());
            return productRepository.save(existing);
        }
        return null;
    }

    public void deleteProduct(Long id) {
        findProductById(id);
        productRepository.deleteById(id);
    }

    public List<ProductSearchDTO> searchProducts(String name, String category) {
        List<Product> products;
        if(name!=null && category!=null) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(name, category);
        } else if(name!=null) {
            products = productRepository.findByNameContainingIgnoreCase(name);
        } else if(category!=null) {
            products = productRepository.findByCategoryIgnoreCase(category);
        } else {
            products = getAllProducts();
        }

        return products.stream().map(
                p -> new ProductSearchDTO(
                        p.getId(),
                        p.getName(),
                        p.getCategory(),
                        p.getQuantity(),
                        p.getPrice()))
                .toList();
    }

    public Map<String, List<ProductReportDTO>> getProductsGroupedByCategory() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.mapping(p -> new ProductReportDTO(p.getName(), p.getQuantity(), p.getPrice()),
                                Collectors.toList())));
    }
}

package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.ProductReportDTO;
import com.kamthan.InventoryPro.dto.ProductResponseDTO;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.ProductMapper;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.model.enums.UnitOfMeasure;
import com.kamthan.InventoryPro.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private StockMovementService stockMovementService;

    public ProductResponseDTO addProduct(Product product) {
        if (product.getUnitOfMeasure() == null) {
            product.setUnitOfMeasure(UnitOfMeasure.PIECE);
        }
        int openingQty = product.getQuantity() != null ? product.getQuantity() : 0;
        product.setQuantity(openingQty);
        Product savedProduct = productRepository.save(product);
        if (openingQty > 0) {
            stockMovementService.recordMovement(
                    savedProduct,
                    MovementType.IN,
                    openingQty,
                    0,
                    openingQty,
                    ReferenceType.OPENING_STOCK,
                    null
            );
        }
        return productMapper.toResponseDTO(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        log.info("Test: Lombok logging is working");
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findProductById(Long id) {
        return productMapper.toResponseDTO(productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found with id: "+id)));
    }
    public ProductResponseDTO updateProduct(Long id, Product updatedProduct) {
        Product existing = productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product not found with id: "+id));
        if (existing != null) {
            existing.setName(updatedProduct.getName());
            existing.setCategory(updatedProduct.getCategory());
            existing.setPrice(updatedProduct.getPrice());
            existing.setQuantity(updatedProduct.getQuantity());
            existing.setUnitOfMeasure(updatedProduct.getUnitOfMeasure());
            return productMapper.toResponseDTO(productRepository.save(existing));
        }
        return null;
    }

    public void deleteProduct(Long id) {
        findProductById(id);
        productRepository.deleteById(id);
    }

    public List<ProductResponseDTO> searchProducts(String name, String category) {
        List<Product> products;
        if(name!=null && category!=null) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(name, category);
        } else if(name!=null) {
            products = productRepository.findByNameContainingIgnoreCase(name);
        } else if(category!=null) {
            products = productRepository.findByCategoryIgnoreCase(category);
        } else {
            return getAllProducts();
        }

        return products.stream()
                .map(productMapper::toResponseDTO)
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

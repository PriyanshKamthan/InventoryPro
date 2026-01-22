package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.ProductReportDTO;
import com.kamthan.InventoryPro.dto.ProductResponseDTO;
import com.kamthan.InventoryPro.exception.InvalidRequestException;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.ProductMapper;
import com.kamthan.InventoryPro.model.Product;
import com.kamthan.InventoryPro.model.StockMovement;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.model.enums.UnitOfMeasure;
import com.kamthan.InventoryPro.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

        log.info("Adding product: name={}, category={}, initialQty={}",
                product.getName(), product.getCategory(), product.getQuantity());

        Product savedProduct = productRepository.save(product);

        if (openingQty > 0) {
            log.info("Adding StockMovement: productName={}, afterQty{}, referenceType={}",
                    savedProduct.getName(), openingQty, ReferenceType.OPENING_STOCK);
            StockMovement sm =
                    stockMovementService.recordMovement(
                            savedProduct,
                            MovementType.IN,
                            openingQty,
                            0,
                            openingQty,
                            ReferenceType.OPENING_STOCK,
                            null
                    );
            log.info("StockMovement added successfully with id={}", sm.getId());
        }

        log.info("Product added successfully with id={}", savedProduct.getId());
        return productMapper.toResponseDTO(savedProduct);
    }

    public List<ProductResponseDTO> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll().stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findProductById(Long id) {
        return productMapper.toResponseDTO(productRepository.findById(id).orElseThrow(() -> {
            log.info("Product not found, id={}",id);
            return new ResourceNotFoundException("Product not found, id: "+id);
        }));
    }

    public ProductResponseDTO updateProduct(Long id, Product updatedProduct) {
        log.info("Updating Product: id={}", id);

        Product existing = productRepository.findById(id).orElseThrow(() -> {
            log.error("Product not found for update, id={}", id);
            return new ResourceNotFoundException("Product not found for update, id: " + id);
        });

        if(!Objects.equals(existing.getQuantity(), updatedProduct.getQuantity()) && updatedProduct.getQuantity()!=null) {
            log.warn("Product quantity can not be updated, id={}", id);
            throw new InvalidRequestException("Product quantity can not be updated");
        }

        existing.setName(updatedProduct.getName());
        existing.setCategory(updatedProduct.getCategory());
        existing.setPrice(updatedProduct.getPrice());
        existing.setUnitOfMeasure(updatedProduct.getUnitOfMeasure());

        Product saved = productRepository.save(existing);

        log.info("Product updated successfully with id={}",saved.getId());
        return productMapper.toResponseDTO(saved);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id={}",id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found for deletion, id={}", id);
                    return new ResourceNotFoundException("Product not found for deletion, id " + id);
                });

        productRepository.delete(product);

        log.info("Product deleted successfully, id={}", id);
    }

    public List<ProductResponseDTO> searchProducts(String name, String category) {
        log.info("Searching products with filters: name={}, category={}", name, category);
        List<Product> products;
        if (name != null && category != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategoryIgnoreCase(name, category);
        } else if (name != null) {
            products = productRepository.findByNameContainingIgnoreCase(name);
        } else if (category != null) {
            products = productRepository.findByCategoryIgnoreCase(category);
        } else {
            return getAllProducts();
        }

        log.info("Product search result count={}", products.size());
        return products.stream()
                .map(productMapper::toResponseDTO)
                .toList();
    }

    public Map<String, List<ProductReportDTO>> getProductsGroupedByCategory() {
        log.info("Fetching category-wise product report");
        List<Product> products = productRepository.findAll();
        log.info("Total products considered for category report={}", products.size());

        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory,
                        Collectors.mapping(p -> new ProductReportDTO(p.getName(), p.getQuantity(), p.getPrice()),
                                Collectors.toList())));
    }
}

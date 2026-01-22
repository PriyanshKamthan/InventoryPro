package com.kamthan.InventoryPro.model;

import com.kamthan.InventoryPro.model.enums.UnitOfMeasure;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@SQLDelete(sql = "UPDATE product SET is_active = false, deleted_at = now() WHERE id = ?")
@Where(clause = "is_active = true")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private Integer quantity;
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit_of_measure")
    private UnitOfMeasure unitOfMeasure;

    @Column(nullable = false)
    private Boolean isActive = true;

    private LocalDateTime deletedAt;

    public Product() {
    }

    public Product(Long id, String name, String category, Integer quantity, Double price, UnitOfMeasure unitOfMeasure) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", unitOfMeasure=" + unitOfMeasure +
                '}';
    }
}
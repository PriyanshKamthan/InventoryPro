package com.kamthan.InventoryPro.model;

import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType movementType;

    private Integer quantity;
    private Integer beforeQty;
    private Integer afterQty;
    private ReferenceType referenceType;
    private Long referenceId;
    private LocalDateTime timestamp;
//    private String performedBy;


    public StockMovement() {
    }

    public StockMovement(Long id, Product product, MovementType movementType, Integer quantity, Integer beforeQty, Integer afterQty, ReferenceType referenceType, Long referenceId, LocalDateTime timestamp) {
        this.id = id;
        this.product = product;
        this.movementType = movementType;
        this.quantity = quantity;
        this.beforeQty = beforeQty;
        this.afterQty = afterQty;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBeforeQty() {
        return beforeQty;
    }

    public void setBeforeQty(Integer beforeQty) {
        this.beforeQty = beforeQty;
    }

    public Integer getAfterQty() {
        return afterQty;
    }

    public void setAfterQty(Integer afterQty) {
        this.afterQty = afterQty;
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "StockMovement{" +
                "id=" + id +
                ", product=" + product +
                ", movementType=" + movementType +
                ", quantity=" + quantity +
                ", beforeQty=" + beforeQty +
                ", afterQty=" + afterQty +
                ", referenceType=" + referenceType +
                ", referenceId=" + referenceId +
                ", timestamp=" + timestamp +
                '}';
    }
}

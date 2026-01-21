package com.kamthan.InventoryPro.dto;

import java.time.LocalDateTime;

public class StockMovementResponseDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String movementType;
    private Integer quantity;
    private Integer beforeQuantity;
    private Integer afterQuantity;
    private String referenceType;
    private Long referenceId;
    private LocalDateTime timestamp;

    public StockMovementResponseDTO() {
    }

    public StockMovementResponseDTO(Long id, Long productId, String productName, String movementType, Integer quantity, Integer beforeQuantity, Integer afterQuantity, String referenceType, Long referenceId, LocalDateTime timestamp) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.movementType = movementType;
        this.quantity = quantity;
        this.beforeQuantity = beforeQuantity;
        this.afterQuantity = afterQuantity;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBeforeQuantity() {
        return beforeQuantity;
    }

    public void setBeforeQuantity(Integer beforeQuantity) {
        this.beforeQuantity = beforeQuantity;
    }

    public Integer getAfterQuantity() {
        return afterQuantity;
    }

    public void setAfterQuantity(Integer afterQuantity) {
        this.afterQuantity = afterQuantity;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
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
        return "StockMovementResponseDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", product=" + productName +
                ", movementType='" + movementType + '\'' +
                ", quantity=" + quantity +
                ", beforeQuantity=" + beforeQuantity +
                ", afterQuantity=" + afterQuantity +
                ", referenceType='" + referenceType + '\'' +
                ", referenceId=" + referenceId +
                ", timestamp=" + timestamp +
                '}';
    }
}

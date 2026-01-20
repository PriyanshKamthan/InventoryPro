package com.kamthan.InventoryPro.dto;

public class PurchaseItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double pricePerUnit;
    private Double taxAmount;

    public PurchaseItemResponseDTO() {
    }

    public PurchaseItemResponseDTO(Long productId, String productName, Integer quantity, Double pricePerUnit, Double taxAmount) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.taxAmount = taxAmount;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Override
    public String toString() {
        return "PurchaseItemResponseDTO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", taxAmount=" + taxAmount +
                '}';
    }
}
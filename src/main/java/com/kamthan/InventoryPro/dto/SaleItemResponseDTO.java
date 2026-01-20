package com.kamthan.InventoryPro.dto;

public class SaleItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double pricePerUnit;
    private Double taxAmount;

    public SaleItemResponseDTO() {
    }

    public SaleItemResponseDTO(Long productId, String productName, Integer quantity, Double pricePerUnit, Double taxAmount) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.taxAmount = taxAmount;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Override
    public String toString() {
        return "SaleItemResponseDTO{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", taxAmount=" + taxAmount +
                '}';
    }
}

package com.kamthan.InventoryPro.dto;

public class ProductReportDTO {
    private String name;
    private Integer quantity;
    private Double pricePerUnit;
    private Double totalValue;

    public ProductReportDTO(String name, Integer quantity, Double pricePerUnit) {
        this.name = name;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalValue = quantity * pricePerUnit;
    }

    public ProductReportDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    @Override
    public String toString() {
        return "ProductReportDTO{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", totalValue=" + totalValue +
                '}';
    }
}
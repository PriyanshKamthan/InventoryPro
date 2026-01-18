package com.kamthan.InventoryPro.model;

import jakarta.persistence.*;

@Entity
public class SaleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private Double pricePerUnit;
    private Double taxAmount;

    @ManyToOne
    private Sale sale;

    public SaleItem() {
    }

    public SaleItem(Long id, Product product, Integer quantity, Double pricePerUnit, Double taxAmount, Sale sale) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.taxAmount = taxAmount;
        this.sale = sale;
    }

    // getters and setters

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

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", taxAmount=" + taxAmount +
                ", sale=" + sale +
                '}';
    }
}

package com.kamthan.InventoryPro.model;

import jakarta.persistence.*;

@Entity
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Integer quantity;
    private Double pricePerUnit;
    private Double taxAmount;

    @ManyToOne
    private Purchase purchase;

    public PurchaseItem() {
    }

    public PurchaseItem(Long id, Product product, Integer quantity, Double pricePerUnit, Double taxAmount, Purchase purchase) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.taxAmount = taxAmount;
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "PurchaseItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", pricePerUnit=" + pricePerUnit +
                ", taxAmount=" + taxAmount +
                ", purchase=" + purchase +
                '}';
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

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}

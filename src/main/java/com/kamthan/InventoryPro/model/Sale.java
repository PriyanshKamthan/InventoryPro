package com.kamthan.InventoryPro.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime saleDate;
    private Double totalAmount;
    private Double taxAmount;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items;

    public Sale() {
    }

    public Sale(Long id, LocalDateTime saleDate, Double totalAmount, Double taxAmount, Customer customer, List<SaleItem> items) {
        this.id = id;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.customer = customer;
        this.items = items;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public void setItems(List<SaleItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", saleDate=" + saleDate +
                ", totalAmount=" + totalAmount +
                ", taxAmount=" + taxAmount +
                ", customer='" + customer + '\'' +
                ", items=" + items +
                '}';
    }
}
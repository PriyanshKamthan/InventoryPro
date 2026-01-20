package com.kamthan.InventoryPro.dto;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseResponseDTO {
    private Long purchaseId;
    private LocalDateTime purchaseDate;
    private String supplierName;
    private Double totalAmount;
    private Double totalTax;
    private List<PurchaseItemResponseDTO> items;

    public PurchaseResponseDTO() {
    }

    public PurchaseResponseDTO(Long purchaseId, LocalDateTime purchaseDate, String supplierName, Double totalAmount, Double totalTax, List<PurchaseItemResponseDTO> items) {
        this.purchaseId = purchaseId;
        this.purchaseDate = purchaseDate;
        this.supplierName = supplierName;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.items = items;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public List<PurchaseItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<PurchaseItemResponseDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PurchaseResponseDTO{" +
                "purchaseId=" + purchaseId +
                ", purchaseDate=" + purchaseDate +
                ", supplierName='" + supplierName + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalTax=" + totalTax +
                ", items=" + items +
                '}';
    }
}
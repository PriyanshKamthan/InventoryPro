package com.kamthan.InventoryPro.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SaleResponseDTO {
    private Long saleId;
    private LocalDateTime saleDate;
    private String customerName;
    private Double totalAmount;
    private Double totalTax;
    private List<SaleItemResponseDTO> items;

    public SaleResponseDTO() {
    }

    public SaleResponseDTO(Long saleId, LocalDateTime saleDate, String customerName, Double totalAmount, Double totalTax, List<SaleItemResponseDTO> items) {
        this.saleId = saleId;
        this.saleDate = saleDate;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.totalTax = totalTax;
        this.items = items;
    }

    public Long getSaleId() {
        return saleId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public List<SaleItemResponseDTO> getItems() {
        return items;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public void setItems(List<SaleItemResponseDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SaleResponseDTO{" +
                "saleId=" + saleId +
                ", saleDate=" + saleDate +
                ", customerName='" + customerName + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalTax=" + totalTax +
                ", items=" + items +
                '}';
    }
}

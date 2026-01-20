package com.kamthan.InventoryPro.mapper;

import com.kamthan.InventoryPro.dto.SaleItemResponseDTO;
import com.kamthan.InventoryPro.dto.SaleResponseDTO;
import com.kamthan.InventoryPro.model.Sale;
import com.kamthan.InventoryPro.model.SaleItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SaleMapper {

    public SaleResponseDTO toResponseDTO(Sale sale) {
        SaleResponseDTO dto = new SaleResponseDTO();

        dto.setSaleId(sale.getId());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setTotalTax(sale.getTaxAmount());

        if(sale.getCustomer()!=null) dto.setCustomerName(sale.getCustomer().getName());

        List<SaleItemResponseDTO> items = sale.getItems().stream()
                .map(this::toItemDTO)
                .toList();

        dto.setItems(items);
        return dto;
    }

    private SaleItemResponseDTO toItemDTO(SaleItem item) {
        SaleItemResponseDTO dto = new SaleItemResponseDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPricePerUnit(item.getPricePerUnit());
        dto.setTaxAmount(item.getTaxAmount());
        return dto;
    }
}

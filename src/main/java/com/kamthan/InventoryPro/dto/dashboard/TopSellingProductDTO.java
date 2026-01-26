package com.kamthan.InventoryPro.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class TopSellingProductDTO {
    private Long productId;
    private String productName;
    private Long totalQuantitySold;
}
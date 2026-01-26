package com.kamthan.InventoryPro.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSummaryDTO {

    private long totalProducts;
    private long totalQuantity;
}
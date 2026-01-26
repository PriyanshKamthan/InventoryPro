package com.kamthan.InventoryPro.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class SalesSummaryDTO {
    private long todaySalesCount;
    private double todaySalesAmount;
    private long monthlySalesCount;
    private double monthlySalesAmount;
}

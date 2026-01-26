package com.kamthan.InventoryPro.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
@AllArgsConstructor
public class PurchaseSummaryDTO {
    private long todayPurchaseCount;
    private double todayPurchaseAmount;
    private long monthlyPurchaseCount;
    private double monthlyPurchaseAmount;
}
package com.kamthan.InventoryPro.dto.invoice;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class InvoiceItemDTO {

    private String productName;
    private int quantity;
    private double pricePerUnit;
    private double taxPerUnit;
    private double lineTotal;
}

package com.kamthan.InventoryPro.dto.invoice;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class CustomerInvoiceDTO {

    private String name;
    private String gstNumber;
    private String phone;
    private String address;
}

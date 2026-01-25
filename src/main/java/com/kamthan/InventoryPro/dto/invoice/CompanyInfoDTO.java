package com.kamthan.InventoryPro.dto.invoice;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class CompanyInfoDTO {
    private String name;
    private String gstNumber;
    private String address;
    private String phone;
    private String email;
}
package com.kamthan.InventoryPro.dto.invoice;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString
public class InvoiceResponseDTO {

    private String invoiceNumber;
    private LocalDate invoiceDate;

    private CompanyInfoDTO company;
    private CustomerInvoiceDTO customer;

    private List<InvoiceItemDTO> items;

    private double subTotal;
    private double taxAmount;
    private double grandTotal;
}

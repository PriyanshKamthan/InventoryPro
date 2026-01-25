package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.invoice.InvoiceResponseDTO;
import com.kamthan.InventoryPro.service.invoice.InvoicePdfService;
import com.kamthan.InventoryPro.service.invoice.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoicePdfService invoicePdfService;

    @GetMapping("/sale/{saleId}")
    public ApiResponse<InvoiceResponseDTO> getInvoiceBySaleId(@PathVariable("saleId") Long saleId) {
        return new ApiResponse<>(true, "Invoice fetched successfully", invoiceService.generateInvoiceForSale(saleId));
    }

    @GetMapping("/sale/{saleId}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long saleId) {

        InvoiceResponseDTO invoice = invoiceService.generateInvoiceForSale(saleId);
        byte[] pdf = invoicePdfService.generateInvoicePdf(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + invoice.getInvoiceNumber() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}

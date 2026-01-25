package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.invoice.InvoiceResponseDTO;
import com.kamthan.InventoryPro.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/sale/{saleId}")
    public ApiResponse<InvoiceResponseDTO> getInvoiceBySaleId(@PathVariable("saleId") Long saleId) {
        return new ApiResponse<>(true, "Invoice fetched successfully", invoiceService.generateInvoiceForSale(saleId));
    }
}

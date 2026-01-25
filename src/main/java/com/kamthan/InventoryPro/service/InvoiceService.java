package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.config.CompanyProperties;
import com.kamthan.InventoryPro.dto.invoice.CompanyInfoDTO;
import com.kamthan.InventoryPro.dto.invoice.CustomerInvoiceDTO;
import com.kamthan.InventoryPro.dto.invoice.InvoiceItemDTO;
import com.kamthan.InventoryPro.dto.invoice.InvoiceResponseDTO;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.model.Customer;
import com.kamthan.InventoryPro.model.Sale;
import com.kamthan.InventoryPro.model.SaleItem;
import com.kamthan.InventoryPro.repository.SaleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InvoiceService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CompanyProperties companyProperties;

    public InvoiceResponseDTO generateInvoiceForSale(Long saleId) {
        log.info("Initiated Invoice generation for sale, id:{}",saleId);
        Sale sale = saleRepository.findById(saleId).orElseThrow(()-> {
            log.error("Sale not found with id:{}",saleId);
           return new ResourceNotFoundException("Sale not found with id:"+saleId);
        });

        InvoiceResponseDTO invoice = new InvoiceResponseDTO();

        String invoiceNumber = "INV-"+sale.getId();
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setInvoiceDate(sale.getSaleDate().toLocalDate());
        log.info("Invoice basic info prepared | invoiceNumber={}", invoiceNumber);

        //Setting CompanyInfo
        CompanyInfoDTO company = new CompanyInfoDTO();
        company.setName(companyProperties.getName());
        company.setGstNumber(companyProperties.getGstNumber());
        company.setAddress(companyProperties.getAddress());
        company.setPhone(companyProperties.getPhone());
        company.setEmail(companyProperties.getEmail());

        invoice.setCompany(company);

        //Setting CustomerInfo
        Customer customer = sale.getCustomer();
        CustomerInvoiceDTO customerDto = new CustomerInvoiceDTO();
        customerDto.setName(customer.getName());
        customerDto.setGstNumber(customer.getGstNumber());
        customerDto.setPhone(customer.getPhone());
        customerDto.setAddress(customer.getAddress());

        invoice.setCustomer(customerDto);

        log.debug("Customer info attached | customerName={}", customer.getName());

        //Setting Items
        List<InvoiceItemDTO> items = new ArrayList<>();
        double subTotal = 0;
        double totalTax = 0;
        for(SaleItem item: sale.getItems()) {
            InvoiceItemDTO itemDto = new InvoiceItemDTO();
            itemDto.setProductName(item.getProduct().getName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPricePerUnit(item.getPricePerUnit());
            itemDto.setTaxPerUnit(item.getTaxAmount());

            double lineTotal = item.getQuantity() * (item.getPricePerUnit() + item.getTaxAmount());
            itemDto.setLineTotal(lineTotal);

            subTotal += item.getPricePerUnit() * item.getQuantity();
            totalTax += item.getTaxAmount() * item.getQuantity();

            items.add(itemDto);

            log.debug("Invoice item added | product={} | qty={} | lineTotal={}",
                    itemDto.getProductName(), itemDto.getQuantity(), lineTotal
            );
        }
        invoice.setItems(items);
        invoice.setSubTotal(subTotal);
        invoice.setTaxAmount(totalTax);
        invoice.setGrandTotal(subTotal+totalTax);

        log.info("Invoice generated successfully | invoiceNumber={} | grandTotal={}",
                invoiceNumber, invoice.getGrandTotal()
        );
        return invoice;
    }
}

package com.kamthan.InventoryPro.service.invoice;

import com.kamthan.InventoryPro.dto.invoice.CompanyInfoDTO;
import com.kamthan.InventoryPro.dto.invoice.CustomerInvoiceDTO;
import com.kamthan.InventoryPro.dto.invoice.InvoiceItemDTO;
import com.kamthan.InventoryPro.dto.invoice.InvoiceResponseDTO;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class InvoicePdfService {

    public byte[] generateInvoicePdf(InvoiceResponseDTO invoice) {

        log.info("PDF generation started | invoiceNumber={}", invoice.getInvoiceNumber());

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            addCompanyHeader(document, invoice);
            addInvoiceInfo(document, invoice);
            addCustomerInfo(document, invoice);
            addItemsTable(document, invoice);
            addTotals(document, invoice);

            document.close();

            log.info("PDF generated successfully | invoiceNumber={}", invoice.getInvoiceNumber());
            return out.toByteArray();

        } catch (Exception e) {
            log.error("Failed to generate invoice PDF", e);
            throw new RuntimeException("Unable to generate invoice PDF");
        }
    }

    private void addCompanyHeader(Document document, InvoiceResponseDTO invoice)
            throws DocumentException, IOException {

        CompanyInfoDTO company = invoice.getCompany();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{70, 30});

        // LEFT: Company text
        Paragraph companyText = new Paragraph();
        companyText.add(new Phrase(company.getName() + "\n", titleFont));
        companyText.add(new Phrase(
                company.getAddress() + "\n" +
                        "GST: " + company.getGstNumber() + "\n" +
                        "Phone: " + company.getPhone() + " | Email: " + company.getEmail(),
                normalFont
        ));

        PdfPCell leftCell = new PdfPCell(companyText);
        leftCell.setBorder(Rectangle.NO_BORDER);

        // RIGHT: Logo
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        try {
            ClassPathResource logoFile = new ClassPathResource("static/logo.png");
            Image logo = Image.getInstance(logoFile.getInputStream().readAllBytes());

            logo.scaleToFit(120, 60);
            logo.setAlignment(Image.ALIGN_RIGHT);

            rightCell.addElement(logo);
        } catch (Exception e) {
            log.warn("Company logo not found, skipping logo rendering");
        }

        table.addCell(leftCell);
        table.addCell(rightCell);

        table.setSpacingAfter(20);
        document.add(table);
    }


//    private void addCompanyHeader(Document document, InvoiceResponseDTO invoice)
//            throws DocumentException {
//
//        CompanyInfoDTO company = invoice.getCompany();
//
//        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
//        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
//
//        Paragraph title = new Paragraph(company.getName(), titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//
//        Paragraph details = new Paragraph(
//                company.getAddress() + "\n" +
//                        "GST: " + company.getGstNumber() + "\n" +
//                        "Phone: " + company.getPhone() + " | Email: " + company.getEmail(),
//                normalFont
//        );
//        details.setAlignment(Element.ALIGN_CENTER);
//        details.setSpacingAfter(20);
//        document.add(details);
//    }

    private void addInvoiceInfo(Document document, InvoiceResponseDTO invoice)
            throws DocumentException {

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        table.addCell(getCell("Invoice No:", font));
        table.addCell(getCell(invoice.getInvoiceNumber(), font));

        table.addCell(getCell("Invoice Date:", font));
        table.addCell(getCell(invoice.getInvoiceDate().toString(), font));

        table.setSpacingAfter(15);
        document.add(table);
    }

    private void addCustomerInfo(Document document, InvoiceResponseDTO invoice)
            throws DocumentException {

        CustomerInvoiceDTO customer = invoice.getCustomer();
        Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font normal = FontFactory.getFont(FontFactory.HELVETICA, 10);

        Paragraph heading = new Paragraph("Bill To:", bold);
        document.add(heading);

        Paragraph details = new Paragraph(
                customer.getName() + "\n" +
                        customer.getAddress() + "\n" +
                        "GST: " + customer.getGstNumber() + "\n" +
                        "Phone: " + customer.getPhone(),
                normal
        );

        details.setSpacingAfter(15);
        document.add(details);
    }

    private void addItemsTable(Document document, InvoiceResponseDTO invoice)
            throws DocumentException {

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1, 3, 1, 1, 1, 1});

        addHeader(table, "S. no.", headerFont);
        addHeader(table, "Product", headerFont);
        addHeader(table, "Qty", headerFont);
        addHeader(table, "Unit Price", headerFont);
        addHeader(table, "Unit Tax", headerFont);
        addHeader(table, "Total", headerFont);
        //addHeader(table, " ", headerFont);

        for(int i=0; i<invoice.getItems().size(); i++) {

            InvoiceItemDTO item = invoice.getItems().get(i);

            table.addCell(getCell(String.valueOf(i+1), cellFont));
            table.addCell(getCell(item.getProductName(), cellFont));
            table.addCell(getCell(String.valueOf(item.getQuantity()), cellFont));
            table.addCell(getCell(String.valueOf(item.getPricePerUnit()), cellFont));
            table.addCell(getCell(String.valueOf(item.getTaxPerUnit()), cellFont));
            table.addCell(getCell(String.valueOf(item.getLineTotal()), cellFont));
            //table.addCell(getCell("", cellFont));
        }

        table.setSpacingAfter(15);
        document.add(table);
    }

    private void addTotals(Document document, InvoiceResponseDTO invoice)
            throws DocumentException {

        Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(40);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);

        table.addCell(getCell("Subtotal", bold));
        table.addCell(getCell(String.valueOf(invoice.getSubTotal()), bold));

        table.addCell(getCell("Tax", bold));
        table.addCell(getCell(String.valueOf(invoice.getTaxAmount()), bold));

        table.addCell(getCell("Grand Total", bold));
        table.addCell(getCell(String.valueOf(invoice.getGrandTotal()), bold));

        document.add(table);
    }

    private PdfPCell getCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        return cell;
    }

    private void addHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);
    }
}

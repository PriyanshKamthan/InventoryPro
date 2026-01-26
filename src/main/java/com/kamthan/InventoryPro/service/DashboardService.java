package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.dashboard.*;
import com.kamthan.InventoryPro.model.enums.MovementType;
import com.kamthan.InventoryPro.model.enums.ReferenceType;
import com.kamthan.InventoryPro.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DashboardService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Autowired
    private SaleItemRepository saleItemRepository;

    public StockSummaryDTO getStockSummary() {

        log.info("Fetching stock summary");

        Object result = productRepository.fetchStockSummary();
        Object[] row = (Object[]) result;

        long totalProducts = (Long) row[0];
        long totalQuantity = (Long) row[1];

        log.info("Stock summary fetched | totalProducts={} | totalQuantity={}", totalProducts, totalQuantity);

        return new StockSummaryDTO(totalProducts, totalQuantity);
    }

    public SalesSummaryDTO getSalesSummary() {

        log.info("Fetching sales summary");

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(23, 59, 59);

        LocalDateTime startOfMonth =
                today.withDayOfMonth(1).atStartOfDay();

        Object todayResult =
                saleRepository.fetchSaleSummary(startOfToday, endOfToday);

        Object monthResult =
                saleRepository.fetchSaleSummary(startOfMonth, endOfToday);

        Object[] todayRow = (Object[]) todayResult;
        Object[] monthRow = (Object[]) monthResult;

        long todayCount = (Long) todayRow[0];
        double todayAmount = (Double) todayRow[1];

        long monthCount = (Long) monthRow[0];
        double monthAmount = (Double) monthRow[1];

        log.info(
                "Sales summary | todayCount={} | todayAmount={} | monthCount={} | monthAmount={}",
                todayCount, todayAmount, monthCount, monthAmount
        );

        return new SalesSummaryDTO(todayCount, todayAmount, monthCount, monthAmount);
    }

    public PurchaseSummaryDTO getPurchaseSummary() {

        log.info("Fetching purchase summary");

        LocalDate today = LocalDate.now();
        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(23, 59, 59);

        LocalDateTime startOfMonth =
                today.withDayOfMonth(1).atStartOfDay();

        Object todayResult =
                purchaseRepository.fetchPurchaseSummary(startOfToday, endOfToday);

        Object monthResult =
                purchaseRepository.fetchPurchaseSummary(startOfMonth, endOfToday);

        Object[] todayRow = (Object[]) todayResult;
        Object[] monthRow = (Object[]) monthResult;

        long todayCount = (Long) todayRow[0];
        double todayAmount = (Double) todayRow[1];

        long monthCount = (Long) monthRow[0];
        double monthAmount = (Double) monthRow[1];

        log.info(
                "Purchase summary | todayCount={} | todayAmount={} | monthCount={} | monthAmount={}",
                todayCount, todayAmount, monthCount, monthAmount
        );

        return new PurchaseSummaryDTO(todayCount, todayAmount, monthCount, monthAmount);
    }

    public List<RecentStockMovementDTO> getRecentStockMovements(int limit) {

        log.info("Fetching last {} stock movements", limit);

        Pageable pageable = PageRequest.of(0, limit);

        List<Object[]> rows =
                stockMovementRepository.findRecentStockMovements(pageable);

        List<RecentStockMovementDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new RecentStockMovementDTO(
                    (LocalDateTime) row[0],
                    (Long) row[1],
                    (String) row[2],
                    (MovementType) row[3],
                    (Integer) row[4],
                    (Integer) row[5],
                    (Integer) row[6],
                    (ReferenceType) row[7],
                    (Long) row[8]
            ));
        }

        log.info("Fetched {} stock movements", result.size());

        return result;
    }
    public List<TopSellingProductDTO> getTopSellingProductsForCurrentMonth(int limit) {

        LocalDateTime startOfMonth =
                LocalDate.now().withDayOfMonth(1).atStartOfDay();
        //LocalDateTime.of(2025, Month.SEPTEMBER, 1, 10, 30, 0);

        LocalDateTime endOfMonth =
                LocalDateTime.now();

        log.info(
                "Fetching top {} selling products between {} and {}",
                limit, startOfMonth, endOfMonth
        );

        Pageable pageable = PageRequest.of(0, limit);

        List<Object[]> rows =
                saleItemRepository.findTopSellingProducts(
                        startOfMonth,
                        endOfMonth,
                        pageable
                );

        List<TopSellingProductDTO> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new TopSellingProductDTO(
                    (Long) row[0],
                    (String) row[1],
                    (Long) row[2]
            ));
        }

        log.info("Top selling products count={}", result.size());

        return result;
    }

}

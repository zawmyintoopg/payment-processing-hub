package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.response.*;
import com.mybank.paymenthub.repository.OperationTransactionRepository;
import com.mybank.paymenthub.repository.projection.*;
import com.mybank.paymenthub.service.TransactionReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionReportServiceImpl
        implements TransactionReportService {

    private final OperationTransactionRepository
            operationTransactionRepository;

    @Override
    public DashboardResponse getDashboardSummary(
            LocalDate fromDate,
            LocalDate toDate
    ){
        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();

        DashboardProjection projection =
                operationTransactionRepository.getDashboardSummary(
                        fromDateTime,
                        toDateTime
                );
        long totalTransactions =
                valueOrZero(projection.getTotalTransactions());

        long successfulTransactions =
                valueOrZero(
                        projection.getSuccessfulTransactions()
                );

        long failedTransactions =
                valueOrZero(
                        projection.getFailedTransactions()
                );

        long pendingTransactions =
                valueOrZero(
                        projection.getPendingTransactions()
                );

        long reversedTransactions =
                valueOrZero(
                        projection.getReversedTransactions()
                );

        BigDecimal totalTransactionAmount =
                decimalOrZero(
                        projection.getTotalTransactionAmount()
                );

        BigDecimal totalMdrAmount =
                decimalOrZero(
                        projection.getTotalMdrAmount()
                );

        BigDecimal totalSettlementAmount =
                decimalOrZero(
                        projection.getTotalSettlementAmount()
                );

        BigDecimal successRate =
                calculatePercentage(
                        successfulTransactions,
                        totalTransactions
                );

        BigDecimal failureRate =
                calculatePercentage(
                        failedTransactions,
                        totalTransactions
                );

        return new DashboardResponse(

                totalTransactions,

                successfulTransactions,

                failedTransactions,

                pendingTransactions,

                reversedTransactions,

                totalTransactionAmount,

                totalMdrAmount,

                totalSettlementAmount,

                successRate,

                failureRate
        );
    }
    // =========================================================
    // Settlement Report
    // =========================================================
    @Override
    public List<SettlementReportResponse> getSettlementReport(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        List<SettlementReportProjection> projections =
                operationTransactionRepository.getSettlementReport(
                        fromDate,
                        toDate
                );

        return projections.stream()
                .map(p -> new SettlementReportResponse(

                        p.getSettlementNumber(),

                        p.getMerchantNumber(),
                        p.getMerchantName(),

                        p.getSettlementDate(),

                        p.getTransactionCount(),

                        p.getTotalTransactionAmount(),
                        p.getTotalMdrAmount(),
                        p.getTotalSettlementAmount(),

                        p.getSettlementStatus()
                ))
                .toList();
    }
    // =========================================================
    // Transaction Report - API
    // =========================================================
    @Override
    public Page<TransactionReportResponse> getTransactionReport(
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable
    ) {

        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();

        Page<TransactionReportProjection> projections =
                operationTransactionRepository.findTransactionReport(
                        fromDateTime,
                        toDateTime,
                        pageable
                );

        return projections.map(this::mapTransactionReport);
    }


    // =========================================================
// Transaction Report - Excel Export
// =========================================================

    @Override
    public List<TransactionReportResponse> getTransactionReportForExport(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();

        List<TransactionReportProjection> projections =
                operationTransactionRepository
                        .findTransactionReportForExport(
                                fromDateTime,
                                toDateTime
                        );

        return projections.stream()
                .map(this::mapTransactionReport)
                .toList();
    }
    // =========================================================
    // Merchant Summary
    // =========================================================

    @Override
    public List<MerchantSummaryResponse> getMerchantSummary(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();

        return operationTransactionRepository.getMerchantSummary(
                fromDateTime,
                toDateTime
        );
    }

    // =========================================================
    // Terminal Summary
    // =========================================================

    @Override
    public List<TerminalSummaryResponse> getTerminalSummary(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();

        List<TerminalSummaryProjection> projections =
                operationTransactionRepository.findTerminalSummary(
                        fromDateTime,
                        toDateTime
                );

        return projections.stream()
                .map(p -> new TerminalSummaryResponse(

                        p.getTerminalNumber(),
                        p.getTerminalName(),

                        p.getMerchantNumber(),
                        p.getMerchantName(),

                        p.getTotalTransactions(),
                        p.getSuccessfulTransactions(),
                        p.getFailedTransactions(),
                        p.getReversedTransactions(),

                        p.getTotalTransactionAmount(),
                        p.getTotalMdrAmount(),
                        p.getTotalSettlementAmount()
                ))
                .toList();
    }
    @Override
    public List<PaymentChannelSummaryResponse> getPaymentChannelSummary(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        LocalDateTime fromDateTime =
                fromDate.atStartOfDay();

        LocalDateTime toDateTime =
                toDate.plusDays(1).atStartOfDay();


        List<PaymentChannelSummaryProjection> projections =
                operationTransactionRepository.findPaymentChannelSummary(
                        fromDateTime,
                        toDateTime
                );


        return projections.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // Transaction Projection -> Response
    // =========================================================

    private TransactionReportResponse mapTransactionReport(
            TransactionReportProjection p
    ) {

        return new TransactionReportResponse(

                p.getTransactionNumber(),

                p.getTransactionTimestamp(),

                p.getReferenceNumber(),

                p.getMerchantNumber(),
                p.getMerchantName(),

                p.getOutletNumber(),
                p.getOutletName(),

                p.getTerminalNumber(),

                p.getPaymentChannel(),
                p.getPaymentMethod(),

                p.getCurrencyCode(),

                p.getTransactionType(),

                p.getTransactionAmount(),

                p.getMdrRate(),

                p.getMdrAmount(),

                p.getSettlementAmount(),

                p.getTransactionStatus(),

                p.getSettlementStatus()
        );
    }
    private long valueOrZero(Long value) {

        return value != null
                ? value
                : 0L;
    }


    private BigDecimal decimalOrZero(BigDecimal value) {

        return value != null
                ? value
                : BigDecimal.ZERO;
    }


    private BigDecimal calculatePercentage(
            long value,
            long total
    ) {

        if (total == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(value)
                .multiply(BigDecimal.valueOf(100))
                .divide(
                        BigDecimal.valueOf(total),
                        2,
                        RoundingMode.HALF_UP
                );
    }



    // =========================================================
    // Projection -> Response
    // =========================================================

    private PaymentChannelSummaryResponse mapToResponse(
            PaymentChannelSummaryProjection p
    ) {

        Long totalTransactions =
                safeLong(p.getTotalTransactions());

        Long successfulTransactions =
                safeLong(p.getSuccessfulTransactions());

        Long failedTransactions =
                safeLong(p.getFailedTransactions());

        Long reversedTransactions =
                safeLong(p.getReversedTransactions());


        BigDecimal totalAmount =
                safeDecimal(
                        p.getTotalTransactionAmount()
                );


        BigDecimal totalMdr =
                safeDecimal(
                        p.getTotalMdrAmount()
                );


        BigDecimal totalSettlement =
                safeDecimal(
                        p.getTotalSettlementAmount()
                );


        // -----------------------------------------------------
        // Success Rate
        // -----------------------------------------------------

        BigDecimal successRate =
                calculateRate(
                        successfulTransactions,
                        totalTransactions
                );


        // -----------------------------------------------------
        // Failure Rate
        // -----------------------------------------------------

        BigDecimal failureRate =
                calculateRate(
                        failedTransactions,
                        totalTransactions
                );


        // -----------------------------------------------------
        // Average Transaction Amount
        // -----------------------------------------------------

        BigDecimal averageTransactionAmount =
                calculateAverage(
                        totalAmount,
                        successfulTransactions
                );


        return new PaymentChannelSummaryResponse(

                p.getChannelCode(),

                p.getPaymentChannel(),

                totalTransactions,

                successfulTransactions,

                failedTransactions,

                reversedTransactions,

                totalAmount,

                totalMdr,

                totalSettlement,

                successRate,

                failureRate,

                averageTransactionAmount
        );
    }


    // =========================================================
    // Calculate Rate
    // =========================================================

    private BigDecimal calculateRate(
            Long value,
            Long total
    ) {

        if (total == null || total == 0) {

            return BigDecimal.ZERO;
        }


        return BigDecimal.valueOf(value)

                .multiply(
                        BigDecimal.valueOf(100)
                )

                .divide(
                        BigDecimal.valueOf(total),
                        2,
                        RoundingMode.HALF_UP
                );
    }


    // =========================================================
    // Calculate Average
    // =========================================================

    private BigDecimal calculateAverage(
            BigDecimal amount,
            Long transactionCount
    ) {

        if (
                amount == null ||
                        transactionCount == null ||
                        transactionCount == 0
        ) {

            return BigDecimal.ZERO;
        }


        return amount.divide(
                BigDecimal.valueOf(transactionCount),
                2,
                RoundingMode.HALF_UP
        );
    }


    // =========================================================
    // Safe Long
    // =========================================================

    private Long safeLong(Long value) {

        return value != null
                ? value
                : 0L;
    }


    // =========================================================
    // Safe BigDecimal
    // =========================================================

    private BigDecimal safeDecimal(
            BigDecimal value
    ) {

        return value != null
                ? value
                : BigDecimal.ZERO;
    }


}
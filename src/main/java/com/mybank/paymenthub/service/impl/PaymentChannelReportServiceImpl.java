package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.response.PaymentChannelSummaryResponse;
import com.mybank.paymenthub.repository.OperationTransactionRepository;
import com.mybank.paymenthub.repository.projection.PaymentChannelSummaryProjection;
import com.mybank.paymenthub.service.PaymentChannelReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentChannelReportServiceImpl
        implements PaymentChannelReportService {

    private final OperationTransactionRepository
            operationTransactionRepository;


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
                operationTransactionRepository
                        .findPaymentChannelSummary(
                                fromDateTime,
                                toDateTime
                        );


        return projections.stream()
                .map(this::mapToResponse)
                .toList();
    }


    private PaymentChannelSummaryResponse mapToResponse(
            PaymentChannelSummaryProjection p
    ) {

        Long totalTransactions =
                safeLong(p.getTotalTransactions());

        Long successfulTransactions =
                safeLong(p.getSuccessfulTransactions());

        Long failedTransactions =
                safeLong(p.getFailedTransactions());


        BigDecimal totalAmount =
                safeDecimal(p.getTotalTransactionAmount());


        // =========================================
        // Success Rate
        // =========================================

        BigDecimal successRate =
                calculateRate(
                        successfulTransactions,
                        totalTransactions
                );


        // =========================================
        // Failure Rate
        // =========================================

        BigDecimal failureRate =
                calculateRate(
                        failedTransactions,
                        totalTransactions
                );


        // =========================================
        // Average Transaction Amount
        // =========================================

        BigDecimal averageTransactionAmount =
                calculateAverage(
                        totalAmount,
                        totalTransactions
                );


        return new PaymentChannelSummaryResponse(

                p.getChannelCode(),

                p.getPaymentChannel(),

                totalTransactions,

                successfulTransactions,

                failedTransactions,

                safeLong(
                        p.getReversedTransactions()
                ),

                totalAmount,

                safeDecimal(
                        p.getTotalMdrAmount()
                ),

                safeDecimal(
                        p.getTotalSettlementAmount()
                ),

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
                .multiply(BigDecimal.valueOf(100))
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
            BigDecimal totalAmount,
            Long totalTransactions
    ) {

        if (
                totalTransactions == null
                        || totalTransactions == 0
        ) {
            return BigDecimal.ZERO;
        }

        return totalAmount.divide(
                BigDecimal.valueOf(totalTransactions),
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

    private BigDecimal safeDecimal(BigDecimal value) {

        return value != null
                ? value
                : BigDecimal.ZERO;
    }
}
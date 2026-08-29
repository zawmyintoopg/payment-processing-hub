package com.mybank.paymenthub.dto.response;

import java.math.BigDecimal;

public record DashboardResponse(

        Long totalTransactions,

        Long successfulTransactions,

        Long failedTransactions,

        Long pendingTransactions,

        Long reversedTransactions,

        BigDecimal totalTransactionAmount,

        BigDecimal totalMdrAmount,

        BigDecimal totalSettlementAmount,

        BigDecimal successRate,

        BigDecimal failureRate
) {
}
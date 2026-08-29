package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailyTrendResponse {

    private LocalDate transactionDate;

    private Long totalTransactions;

    private Long successfulTransactions;

    private Long failedTransactions;

    private Long pendingTransactions;

    private BigDecimal totalTransactionAmount;

    private BigDecimal totalMdrAmount;

    private BigDecimal totalSettlementAmount;
}
package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MonthlyTrendResponse {

    private String month;

    private Long totalTransactions;

    private Long successfulTransactions;

    private Long failedTransactions;

    private Long pendingTransactions;

    private BigDecimal totalTransactionAmount;

    private BigDecimal totalMdrAmount;

    private BigDecimal totalSettlementAmount;
}
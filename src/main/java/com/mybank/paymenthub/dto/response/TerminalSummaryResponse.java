package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TerminalSummaryResponse {

    private String terminalNumber;

    private String terminalName;

    private String merchantNumber;

    private String merchantName;

    private Long totalTransactions;

    private Long successfulTransactions;

    private Long failedTransactions;

    private Long reversedTransactions;

    private BigDecimal totalTransactionAmount;

    private BigDecimal totalMdrAmount;

    private BigDecimal totalSettlementAmount;
}
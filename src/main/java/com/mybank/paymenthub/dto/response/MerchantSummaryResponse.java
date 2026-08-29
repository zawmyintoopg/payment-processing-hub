package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSummaryResponse {

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
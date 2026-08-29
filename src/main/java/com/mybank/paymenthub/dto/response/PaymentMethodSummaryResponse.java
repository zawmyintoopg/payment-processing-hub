package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentMethodSummaryResponse {

    private String paymentCode;
    private String paymentMethod;

    private Long totalTransactions;
    private Long successfulTransactions;
    private Long failedTransactions;
    private Long reversedTransactions;

    private BigDecimal totalTransactionAmount;
    private BigDecimal totalMdrAmount;
    private BigDecimal totalSettlementAmount;
}
package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentChannelSummaryResponse {

    private String channelCode;
    private String paymentChannel;

    private Long totalTransactions;
    private Long successfulTransactions;
    private Long failedTransactions;
    private Long reversedTransactions;

    private BigDecimal totalTransactionAmount;
    private BigDecimal totalMdrAmount;
    private BigDecimal totalSettlementAmount;

    // Analytics
    private BigDecimal successRate;
    private BigDecimal failureRate;
    private BigDecimal averageTransactionAmount;
}
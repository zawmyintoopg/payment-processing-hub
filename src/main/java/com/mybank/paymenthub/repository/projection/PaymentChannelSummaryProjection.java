package com.mybank.paymenthub.repository.projection;

import java.math.BigDecimal;

public interface PaymentChannelSummaryProjection {

    String getChannelCode();

    String getPaymentChannel();

    Long getTotalTransactions();

    Long getSuccessfulTransactions();

    Long getFailedTransactions();

    Long getReversedTransactions();

    BigDecimal getTotalTransactionAmount();

    BigDecimal getTotalMdrAmount();

    BigDecimal getTotalSettlementAmount();
}
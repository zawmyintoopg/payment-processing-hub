package com.mybank.paymenthub.repository.projection;

import java.math.BigDecimal;

public interface PaymentMethodSummaryProjection {

    String getPaymentCode();

    String getPaymentMethod();

    Long getTotalTransactions();

    Long getSuccessfulTransactions();

    Long getFailedTransactions();

    Long getReversedTransactions();

    BigDecimal getTotalTransactionAmount();

    BigDecimal getTotalMdrAmount();

    BigDecimal getTotalSettlementAmount();
}
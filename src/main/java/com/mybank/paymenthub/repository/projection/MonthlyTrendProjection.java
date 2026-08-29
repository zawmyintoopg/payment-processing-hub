package com.mybank.paymenthub.repository.projection;

import java.math.BigDecimal;

public interface MonthlyTrendProjection {

    String getMonth();

    Long getTotalTransactions();

    Long getSuccessfulTransactions();

    Long getFailedTransactions();

    Long getPendingTransactions();

    BigDecimal getTotalTransactionAmount();

    BigDecimal getTotalMdrAmount();

    BigDecimal getTotalSettlementAmount();
}
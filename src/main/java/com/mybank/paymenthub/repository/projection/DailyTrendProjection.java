package com.mybank.paymenthub.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DailyTrendProjection {

    LocalDate getTransactionDate();

    Long getTotalTransactions();

    Long getSuccessfulTransactions();

    Long getFailedTransactions();

    Long getPendingTransactions();

    BigDecimal getTotalTransactionAmount();

    BigDecimal getTotalMdrAmount();

    BigDecimal getTotalSettlementAmount();
}
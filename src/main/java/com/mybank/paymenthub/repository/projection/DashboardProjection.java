package com.mybank.paymenthub.repository.projection;

import java.math.BigDecimal;

public interface DashboardProjection {
    Long getTotalTransactions();
    Long getSuccessfulTransactions();
    Long getFailedTransactions();
    Long getPendingTransactions();
    Long getReversedTransactions();
    BigDecimal getTotalTransactionAmount();
    BigDecimal getTotalMdrAmount();
    BigDecimal getTotalSettlementAmount();
}

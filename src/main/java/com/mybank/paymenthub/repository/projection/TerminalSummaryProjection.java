package com.mybank.paymenthub.repository.projection;

import java.math.BigDecimal;

public interface TerminalSummaryProjection {

    String getTerminalNumber();

    String getTerminalName();

    String getMerchantNumber();

    String getMerchantName();

    Long getTotalTransactions();

    Long getSuccessfulTransactions();

    Long getFailedTransactions();

    Long getReversedTransactions();

    BigDecimal getTotalTransactionAmount();

    BigDecimal getTotalMdrAmount();

    BigDecimal getTotalSettlementAmount();
}
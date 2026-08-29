package com.mybank.paymenthub.repository.projection;

import com.mybank.paymenthub.enums.SettlementStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SettlementReportProjection {

    String getSettlementNumber();

    String getMerchantNumber();

    String getMerchantName();

    LocalDate getSettlementDate();

    Long getTransactionCount();

    BigDecimal getTotalTransactionAmount();

    BigDecimal getTotalMdrAmount();

    BigDecimal getTotalSettlementAmount();

    SettlementStatus getSettlementStatus();
}
package com.mybank.paymenthub.repository.projection;

import com.mybank.paymenthub.enums.SettlementStatus;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionReportProjection {

    String getTransactionNumber();

    LocalDateTime getTransactionTimestamp();

    String getReferenceNumber();

    String getMerchantNumber();

    String getMerchantName();

    String getOutletNumber();

    String getOutletName();

    String getTerminalNumber();

    String getPaymentChannel();

    String getPaymentMethod();

    String getCurrencyCode();

    TransactionType getTransactionType();

    BigDecimal getTransactionAmount();

    BigDecimal getMdrRate();

    BigDecimal getMdrAmount();

    BigDecimal getSettlementAmount();

    TransactionStatus getTransactionStatus();

    SettlementStatus getSettlementStatus();
}
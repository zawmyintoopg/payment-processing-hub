package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.SettlementStatus;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionReportResponse(

        String transactionNumber,

        LocalDateTime transactionTimestamp,

        String referenceNumber,

        String merchantNumber,

        String merchantName,

        String outletNumber,

        String outletName,

        String terminalNumber,

        String paymentChannel,

        String paymentMethod,

        String currency,

        TransactionType transactionType,

        BigDecimal transactionAmount,

        BigDecimal mdrRate,

        BigDecimal mdrAmount,

        BigDecimal settlementAmount,

        TransactionStatus transactionStatus,

        SettlementStatus settlementStatus

) {
}
package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.SettlementStatus;
import com.mybank.paymenthub.enums.TransactionStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationTransactionResponse {

        // ======================================================
        // TRANSACTION
        // ======================================================

        private String transactionNumber;
        private String referenceNumber;
        private String externalReferenceNumber;
        private String providerReferenceNumber;

        private LocalDateTime transactionTimestamp;

        private String transactionType;
        private String transactionTypeName;

        private TransactionStatus transactionStatus;


        // ======================================================
        // MERCHANT
        // ======================================================

        private Long merchantId;
        private String merchantName;

        private Long merchantOutletId;
        private String merchantOutletName;


        // ======================================================
        // TERMINAL
        // ======================================================

        private Long terminalId;
        private String terminalNumber;
        private String terminalType;


        // ======================================================
        // PAYMENT
        // ======================================================
        private Long paymentChannelId;
        private String paymentChannel;
        private Long paymentMethodId;
        private String paymentMethodName;
        private String maskedPaymentAccount;


        // ======================================================
        // QR
        // ======================================================

        private Long merchantQRId;
        private String merchantQRName;


        // ======================================================
        // AMOUNT
        // ======================================================

        private BigDecimal transactionAmount;

        private Long currencyId;
        private String currencyName;


        // ======================================================
        // PROVIDER / CARD
        // ======================================================

        private String authorizationCode;
        private String responseCode;
        private String responseMessage;
        private String retrievalReferenceNumber;


        // ======================================================
        // MDR / SETTLEMENT
        // ======================================================

        private BigDecimal mdrRate;
        private BigDecimal mdrAmount;
        private BigDecimal settlementAmount;

        private SettlementStatus settlementStatus;


        // ======================================================
        // REVERSAL
        // ======================================================

        private String originalTransactionNumber;
        private LocalDateTime reversalDatetime;
        private Boolean isReversed;
        private String reason;
}
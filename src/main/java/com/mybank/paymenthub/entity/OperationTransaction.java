package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.SettlementStatus;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_transactions"
, indexes = {
        @Index(
                name="idx_transaction_number",
                columnList = "transaction_number"
        ),
        @Index(
                name="idx_txn_time",
                columnList = "transaction_timestamp"
        ),
        @Index(
                name="idx_outlet_time",
                columnList = "merchant_outlet_id,transaction_timestamp"
        ),
        @Index(
                name="idx_txn_status",
                columnList = "transaction_status"
        ),
        @Index(
                name = "idx_settlement_status",
                columnList = "settlement_status"
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationTransaction extends BaseEntity {
    @Column(
            name = "transaction_number",
            nullable = false,
            unique = true,
            length = 30
    )
    private String transactionNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_channel_id",
            nullable = false
    )
    private PaymentChannel paymentChannel;

    @Column(
            name = "reference_number",
            length = 50,
            nullable = false
    )
    private String referenceNumber;

    @Column(
            name = "provider_reference_number",
            length = 50
    )
    private String providerReferenceNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "terminal_id",
            nullable = false
    )
    private TerminalInventory terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "merchant_id",
            nullable = false
    )
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "merchant_outlet_id",
            nullable = false
    )
    private MerchantOutlet merchantOutlet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="original_transaction_id")
    private OperationTransaction originalTransaction;

    @Column(
            name = "transaction_timestamp",
            nullable = false
    )
    private LocalDateTime transactionTimestamp;
    @Column(
            name = "reversal_datetime"
    )
    private LocalDateTime reversalDatetime;

    @Column(name = "transaction_amount",
            nullable = false,
            precision = 18,
            scale = 2)
    private BigDecimal transactionAmount;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name="currency_id",
            nullable = false
    )
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "transaction_type",
            nullable = false,
            length = 30
    )
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "transaction_status",
            nullable = false
    )
    private TransactionStatus transactionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="payment_method_id",
            nullable = false
    )
    private PaymentMethod paymentMethod;

    @Column(name = "masked_payment_account", length = 30)
    private String maskedPaymentAccount;

    @Column(name = "authorization_code", length = 50)
    private String authorizationCode;

    @Column(name = "response_code",length = 5)
    private String responseCode;

    @Column(name = "response_message" , length = 100)
    private String responseMessage;

    @Column(name = "retrieval_reference_number",length = 50)
    private String retrievalReferenceNumber;

    @Column(name = "external_reference_number",length = 50)
    private String externalReferenceNumber;

    @Column(name = "mdr_rate",precision = 5,scale = 2)
    private BigDecimal mdrRate;

    @Column(name = "mdr_amount",precision = 18,scale = 2)
    private BigDecimal mdrAmount;

    @Column(name = "settlement_amount",precision = 18,scale = 2)
    private BigDecimal settlementAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "settlement_status")
    private SettlementStatus settlementStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "merchant_qr_id"
    )
    private MerchantQR merchantQR;
    @Column(
            name = "is_reversed",
            nullable = false
    )
    private Boolean isReversed = false;
    @Column(
            name = "reason",
            length = 100
    )
    private String reason;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "settlement_id"
    )
    private Settlement settlement;
}
package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Currency;
import com.mybank.paymenthub.enums.PaymentChannel;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "operation_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationTransaction extends BaseEntity {


    @Column(name = "transaction_number", nullable = false, unique = true, length = 30)
    private String transactionNumber;

    private PaymentChannel channel;

    @Column(name = "reference_number", length = 100)
    private String referenceNumber;
    /*
     * Terminal used during transaction
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_id", nullable = false)
    private TerminalInventory terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_qr_id")
    private MerchantQR merchantQR;
    /*
     * Outlet where payment happened
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_outlet_id", nullable = false)
    private MerchantOutlet merchantOutlet;


    @Column(name = "transaction_date_time", nullable = false)
    private LocalDateTime transactionDateTime;


    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;


    @Column(name = "currency_id", nullable = false)
    private Currency currency;


    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;


    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false)
    private TransactionStatus transactionStatus;


    @Column(name = "payment_method", length = 30)
    private String paymentMethod;
    /*
     * Do not store full card number
     */
    @Column(name = "masked_card_number", length = 30)
    private String maskedCardNumber;


    @Column(name = "authorization_code", length = 50)
    private String authorizationCode;

}
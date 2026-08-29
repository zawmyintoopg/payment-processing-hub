package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.AccountTransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "account_transactions")
public class AccountTransaction extends BaseEntity{

    @Column(
            name = "transaction_number",
            nullable = false,
            length = 30
    )
    private String transactionNumber;

    @Column(
            name = "transactionDate",
            nullable = false
    )
    private LocalDate transactionDate;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "transaction_type",
            nullable = false
    )
    private AccountTransactionType accountTransactionType;
    @Column(
            name = "amount",
            nullable = false
    )
    private BigDecimal amount;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "bank_account_id",
            nullable = false
    )
    private MerchantBankAccount merchantBankAccount;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "settlement_id",
            nullable = false
    )
    private Settlement settlement;
    @Column(
            name = "balance_before",
            nullable = false
    )
    private BigDecimal balance_before;

    @Column(
            name = "balance_after",
            nullable = false
    )
    private BigDecimal balance_after;





}

package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.SettlementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "settlements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settlement extends BaseEntity {
    @Column(name="settlement_number",
            nullable=false,
            unique=true)
    private String settlementNumber;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_id",
            nullable=false)
    private Merchant merchant;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_bank_account_id",
            nullable=false)
    private MerchantBankAccount merchantBankAccount;


    @Column(name="settlement_date",
            nullable=false)
    private LocalDate settlementDate;


    @Column(name="total_amount",
            nullable=false,
            precision=18,
            scale=2)
    private BigDecimal totalAmount;


    @Column(name="transaction_count")
    private Integer transactionCount;


    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private SettlementStatus status;
}

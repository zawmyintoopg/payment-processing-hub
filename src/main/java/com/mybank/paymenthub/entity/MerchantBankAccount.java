package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.AccountStatus;
import com.mybank.paymenthub.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="merchant_bank_accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantBankAccount extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_id",nullable = false)
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="bank_id",nullable = false)
    private Bank bank;

    @Column(name="account_number",nullable = false,unique = true,length = 30)
    private String accountNumber;

    @Column(name="account_name",nullable = false,length = 150)
    private String accountName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_type_id",nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name="currency",nullable = false)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private AccountStatus status;
}

package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_types")
public class AccountType extends BaseEntity{
    private String accountType;
    private String description;
    private Status status;
}

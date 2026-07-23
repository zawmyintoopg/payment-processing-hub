package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="banks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bank extends BaseEntity{

    @Column(name="bank_code",nullable = false,unique = true)//001
    private String bankCode;

    @Column(name="bank_name",nullable = false,unique = true)// Kanbawza
    private String bankName;

    @Column(name="short_name") //KBZ
    private String shortName;

    @Column(name="status",nullable = false)
    private Status status;

}

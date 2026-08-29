package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import com.mybank.paymenthub.enums.TerminalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "payment_methods")
public class PaymentMethod extends BaseEntity{
    @Column(name = "payment_code",nullable = false,unique = true,length = 10)
    private String paymentCode;

    @Column(name = "payment_name",nullable = false,unique = true)
    private String paymentName;

    @Column(name="description")
    private String  description;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private Status status = Status.ACTIVE;
}

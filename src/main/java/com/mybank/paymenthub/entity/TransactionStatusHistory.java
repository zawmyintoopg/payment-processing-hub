package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "transaction_status_histories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatusHistory extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private OperationTransaction transaction;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;


    @Column(name = "changed_date", nullable = false)
    private LocalDateTime changedDate;


    @Column(name = "remarks", length = 500)
    private String remarks;


    @Column(name = "changed_by", length = 50)
    private String changedBy;

}
package com.mybank.paymenthub.entity;
import com.mybank.paymenthub.enums.SettlementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="settlement_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementDetail extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="settlement_id",
            nullable=false)
    private Settlement settlement;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="transaction_id",
            nullable=false)
    private OperationTransaction transaction;


    @Column(name="settlement_amount",
            nullable=false,
            precision=18,
            scale=2)
    private BigDecimal settlementAmount;

}

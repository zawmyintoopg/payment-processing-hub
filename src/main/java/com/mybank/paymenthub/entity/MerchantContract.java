package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.ContractStatus;
import com.mybank.paymenthub.enums.ContractType;
import com.mybank.paymenthub.enums.SettlementCycle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity
@Table(name="merchant_contracts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantContract extends BaseEntity{
    @Column(
            unique = true,
            nullable = false,
            length = 50
    )
    private String contractNumber;
    @OneToOne
    @JoinColumn(
            name="merchant_id",
            nullable = false
    )
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "contract_type",
            nullable = false
    )
    private ContractType contractType;

    @Column(
            name = "contract_date",
            nullable = false
    )
    private LocalDate contractDate;

    @Column(
            name = "contract_start_date",
            nullable = false
    )
    private LocalDate contractStartDate;

    @Column(
            name = "contract_end_date",
            nullable = false
    )
    private LocalDate contractEndDate;

    @Column(
            name = "commission_rate",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal commissionRate;

    @Column(
            name = "settlement_cycle",
            nullable = false,
            length = 10
    )
    @Enumerated(EnumType.STRING)
    private SettlementCycle settlementCycle;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "contract_status",
            length = 20
    )
    private ContractStatus status;
}

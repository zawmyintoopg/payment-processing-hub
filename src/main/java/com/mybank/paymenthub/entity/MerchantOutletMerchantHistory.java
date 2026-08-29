package com.mybank.paymenthub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "merchant_outlet_merchant_history"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantOutletMerchantHistory
        extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "merchant_outlet_id",
            nullable = false
    )
    private MerchantOutlet merchantOutlet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "merchant_id",
            nullable = false
    )
    private Merchant merchant;

    @Column(
            name = "effective_from",
            nullable = false
    )
    private LocalDateTime effectiveFrom;

    @Column(
            name = "effective_to"
    )
    private LocalDateTime effectiveTo;

    @Column(
            name = "change_reason",
            length = 500
    )
    private String changeReason;
}
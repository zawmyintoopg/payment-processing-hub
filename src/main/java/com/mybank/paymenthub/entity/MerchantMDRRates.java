package com.mybank.paymenthub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "merchant_mdr_rates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantMDRRates extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="merchant_id",
            nullable = false
    )
    private Merchant merchant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "payment_method_id",
            nullable = false
    )
    private PaymentMethod paymentMethod;

    @Column(
            name = "mdr_date",
            nullable = false,
            precision = 5,
            scale = 2
    )
    private BigDecimal mdrRate;

}

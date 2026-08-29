package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.enums.OutletStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name="merchant_outlets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantOutlet
        extends BaseEntity{

    @Column(
            name="outlet_number",
            unique = true,nullable = false,
            length = 20
    )
    private String outletNumber; // 300000001

    @Column(
            name = "outlet_name",
            nullable = false,
            length = 150
    )
    private String outletName;

    @Column(
            name="address",
            nullable = false,
            length = 500
    )
    private String address;

    @Column(
            name ="city",
            nullable = false,
            length = 200
    )
    private String city;

    @Column(
            name = "phone",
            length = 50,
            nullable = false
    )
    @Pattern(
            regexp = "^(09|\\+959)[0-9]{7,9}$",
            message = "Invalid phone number"
    )
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="merchant_id",
            nullable = false
    )
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private OutletStatus status;
}

package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="merchant_outlets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantOutlet extends BaseEntity{
    @Column(name="outlet_number",unique = true,nullable = false)
    private String outletNumber; // 300000001

    @Column(name = "outlet_name",nullable = false)
    private String outletName;

    @Column(name="address",nullable = false,length = 500)
    private String address;

    private String city;

    @Column(name = "phone",length = 20)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_id",nullable = false)
    private Merchant merchant;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private MerchantStatus status;

}

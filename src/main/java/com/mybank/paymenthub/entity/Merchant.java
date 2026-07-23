package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="merchants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Merchant extends BaseEntity {

    @Column(name = "merchant_number",nullable = false,unique = true,length = 10)
    private String merchantNumber;

    @Column(name = "merchant_name",nullable = false,length = 150)
    private String merchantName;

    @Column(name = "merchant_registration_no",nullable = false,unique = true,length = 10)
    private String  merchantRegistrationNo;

    @Column(name = "business_registration_date",nullable = false)
    private LocalDate businessRegistrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_owner_id",nullable = false)
    private MerchantOwner merchantOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_segment_id",nullable = false)
    private MerchantSegment merchantSegment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_category_id",nullable = false)
    private MerchantCategory merchantCategory;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private MerchantStatus status;

}

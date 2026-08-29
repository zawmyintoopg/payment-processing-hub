package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.QRStatus;
import com.mybank.paymenthub.enums.QRType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="merchant_qrs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchantQR extends BaseEntity {

    @Column(name="qr_code", nullable=false, unique=true)
    private String qrCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_outlet_id", nullable=false)
    private MerchantOutlet merchantOutlet;

    @Enumerated(EnumType.STRING)
    @Column(name="qr_type", nullable=false)
    private QRType qrType;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private QRStatus status;

    @Column(name="provider_name")
    private String providerName;




}
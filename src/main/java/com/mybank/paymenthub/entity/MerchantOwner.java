package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.MerchantOwnerType;
import com.mybank.paymenthub.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name="merchant_owner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantOwner extends BaseEntity {

    @Column(
            name="owner_number",
            nullable = false,
            unique = true,length =30
    )
    private String ownerNumber;

    @Column(
            name="owner_name",
            nullable = false,
            length =100
    )
    private String ownerName;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name="owner_type",
            nullable = false,
            length = 20
    )
    private MerchantOwnerType ownerType;

    @Column(
            name="registration_no",
            length = 50
    )
    private String registrationNo;

    @Column(
            name="registration_date"
    )
    private LocalDate registrationDate;

    @Column(
            name="phone",
            length = 20,
            nullable = false
    )
    private String phone;

    @Column(
            name = "email",
            length = 100
    )
    private String email;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    private MerchantStatus status;
}

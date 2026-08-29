package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency extends BaseEntity{
    @Column(
            name = "currency_code",
            length = 10,
            nullable = false,
            unique = true
    )
    private String currencyCode;
    @Column(
            name = "currency_description",
            length = 100,
            nullable = false,
            unique = true
    )
    private String getCurrencyDescription;

    @Column(
            name = "status",
            length = 10,
            nullable = false
    )
    private Status status = Status.ACTIVE;
}

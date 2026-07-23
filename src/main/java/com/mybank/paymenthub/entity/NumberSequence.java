package com.mybank.paymenthub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="number_sequences")
@Getter
@Setter
public class NumberSequence {
    @Id
    private String sequenceName;

    @Column(nullable = false)
    private Long currentValue;
}

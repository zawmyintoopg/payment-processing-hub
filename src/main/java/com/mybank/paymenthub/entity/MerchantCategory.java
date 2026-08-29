package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="merchant_categories")
@Getter
@Setter
@NoArgsConstructor
public class MerchantCategory extends BaseEntity {

    @Column(
            name ="category_code",
            nullable = false,
            unique = true,
            length = 20
    )
    private String categoryCode;

    @Column(
            name="category_name",
            nullable = false,
            unique = true,
            length = 100
    )
    private String categoryName;

    @Column(
            length = 255
    )
    private String description;

    @Enumerated(
            EnumType.STRING
    )
    @Column(
            name="category_status",
            nullable = false
    )
    private MerchantCategoryStatus merchantCategoryStatus;
}

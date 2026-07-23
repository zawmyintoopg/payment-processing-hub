package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="merchant_segments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantSegment extends BaseEntity {

    @Column(name="merchant_segment_code",nullable = false,length = 10)
    private String merchantSegmentCode;

    @Column(name="merchant_segment_name",nullable = false,length = 100)
    private String  merchantSegmentName;

    @Enumerated(EnumType.STRING)
    private Status status;
}

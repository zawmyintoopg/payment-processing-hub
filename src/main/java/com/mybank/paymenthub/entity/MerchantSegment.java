package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="merchant_segments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantSegment extends BaseEntity {

    @Column(
            name="merchant_segment_code",
            nullable = false,
            length = 100,
            unique = true
    )
    private String merchantSegmentCode;

    @Column(
            name="merchant_segment_name",
            nullable = false,
            length = 100,
            unique = true
    )
    private String  merchantSegmentName;

    @Enumerated(EnumType.STRING)
    private Status status;
}

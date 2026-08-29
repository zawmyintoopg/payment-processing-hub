package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="payment_channels")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentChannel extends BaseEntity{

    @Column(
            name="channel_code",
            length = 20,
            unique = true
    )
    private String channelCode;

    @Column(
            name = "channel_name",
            length = 50,
            unique = true
    )
    private String channelName;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status"
    )
    private Status status;

}

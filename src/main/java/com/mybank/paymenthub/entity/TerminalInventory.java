package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.TerminalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name="terminal_inventories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalInventory extends BaseEntity{

    @Column(name = "terminal_number",nullable = false,unique = true,length = 10)
    private String terminalNumber;

    @Column(name = "terminal_serial_number",nullable = false,unique = true)
    private String terminalSerialNumber;

    private String manufacturer;

    private String model;

    @Column(name = "purchased_date",nullable = false)
    private LocalDate purchasedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="terminal_type_id",nullable = false)
    private TerminalType terminalType;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false)
    private TerminalStatus status;
}

package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.TerminalAssignmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Table(name="terminal_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalAssignment extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="terminal_inventory_id",nullable = false)
    private TerminalInventory terminalInventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="merchant_outlet_id",nullable = false)
    private MerchantOutlet merchantOutlet;

    @Column(name = "merchant_assigned_date",nullable = false)
    private LocalDate merchantAssignedDate;

    @Column(name="terminated_date")
    private LocalDate terminatedDate;

    @Column(name="installation_date")
    private LocalDate installationDate;

    @Column(name="activation_date")
    private LocalDate activationDate;

    @Column(name="last_transaction_date")
    private LocalDateTime lastTransactionDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private TerminalAssignmentStatus status=TerminalAssignmentStatus.ACTIVE;
}

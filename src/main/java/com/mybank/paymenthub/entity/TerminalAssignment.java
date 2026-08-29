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

    @Column(
            name = "terminal_assignment_number",
            nullable = false,
            unique = true,
            length = 50
    )
    private String terminalAssignmentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="terminal_inventory_id",
            nullable = false
    )
    private TerminalInventory terminalInventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="merchant_outlet_id",nullable = false
    )
    private MerchantOutlet merchantOutlet;

    @Column(
            name = "terminal_assignment_date",
            nullable = false
    )
    private LocalDate terminalAssignmentDate;

    @Column(
            name="terminal_termination_date"
    )
    private LocalDate terminalTerminationDate;

    @Column(
            name="terminal_installation_date"
    )
    private LocalDate terminalInstallationDate;

    @Column(
            name="terminal_activation_date"
    )
    private LocalDate terminalActivationDate;

    @Column(
            name="last_transaction_date"
    )
    private LocalDateTime lastTransactionDate;

    @Enumerated(EnumType.STRING)
    @Column(
            name="status",
            nullable=false
    )
    private TerminalAssignmentStatus status;
}

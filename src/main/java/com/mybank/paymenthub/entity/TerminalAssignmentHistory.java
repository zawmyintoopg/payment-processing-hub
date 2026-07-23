package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.TerminalAssignmentAction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="terminal_assignment_histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalAssignmentHistory extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_assignment_id",nullable = false)
    private TerminalAssignment terminalAssignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "merchant_outlet_id",nullable = false)
    private MerchantOutlet merchantOutlet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "terminal_inventory_id",nullable = false)
    private TerminalInventory terminalInventory;

    @Column(name = "assigned_date",nullable = false)
    private LocalDate assignedDate;

    @Column(name="returned_date",nullable = false)
    private LocalDate returnedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "action")
    private TerminalAssignmentAction action;

    @Column(name="remarks",length = 500)
    private String remarks;

}

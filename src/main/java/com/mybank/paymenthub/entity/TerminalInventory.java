package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.TerminalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "terminal_inventories"
,
uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_terminal_number",
                columnNames = "terminal_number"
        ),
        @UniqueConstraint(
                name="uk_terminal_serial_number",
                columnNames = "terminal_serial_number"
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalInventory extends BaseEntity {

    @Column(
            name = "terminal_number",
            nullable = false,
            unique = true,
            length = 50
    )
    private String terminalNumber;

    @Column(
            name = "terminal_serial_number",
            nullable = false,
            length = 50
    )
    private String terminalSerialNumber;

    @Column(
            name = "manufacturer",
            nullable = false,
            length = 50
    )
    private String manufacturer;

    @Column(
            name = "model",
            nullable = false,
            length = 50
    )
    private String model;

    @Column(
            name = "purchased_date",
            nullable = false
    )
    private LocalDate purchasedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "terminal_type_id",
            nullable = false
    )
    private TerminalType terminalType;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    private TerminalStatus status;
}
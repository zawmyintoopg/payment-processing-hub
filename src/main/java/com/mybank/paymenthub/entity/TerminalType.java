package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "terminal_types",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_terminal_type_code_name",
                        columnNames = {"type_code", "type_name"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalType extends BaseEntity {

    @Column(
            name = "type_code",
            nullable = false,
            length = 30
    )
    private String typeCode;

    @Column(
            name = "type_name",
            nullable = false,
            length = 100
    )
    private String typeName;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "status",
            nullable = false
    )
    private Status status;
}
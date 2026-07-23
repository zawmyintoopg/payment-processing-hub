package com.mybank.paymenthub.entity;

import com.mybank.paymenthub.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "terminal_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalType extends BaseEntity{
    @Column(name="type_code",nullable = false,unique = true)
    private String typeCode;

    @Column(name="type_name",nullable = false,unique = true)
    private String typeName;

    @Column(name="status",nullable = false)
    private Status status;

}

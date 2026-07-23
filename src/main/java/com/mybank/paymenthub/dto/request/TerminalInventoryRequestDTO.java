package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.TerminalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TerminalInventoryRequestDTO {

    @NotBlank(message = "Terminal Serial Number can not be blank")
    private String terminalSerialNumber;
    @NotBlank(message = "Manufacturer can not be blank")
    private String manufacturer;
    @NotBlank(message = "Model can not be blank")
    private String model;
    @NotNull(message = "Purchase Date can not be blank")
    @Column(name = "purchased_date",nullable = false)
    private LocalDate purchasedDate;
    @NotNull(message = "Terminal Type can not be blank")
    private Long terminalTypeId;
    @NotBlank(message = "Terminal Status can not be blank")
    private TerminalStatus status;

}


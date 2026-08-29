package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.TerminalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalInventoryResponse {
    private Long id;
    private String terminalNumber;
    private String terminalSerialNumber;
    private String manufacturer;
    private String model;
    private LocalDate purchasedDate;
    private Long terminalTypeId;
    private String  terminalTypeName;
    private TerminalStatus status;
}

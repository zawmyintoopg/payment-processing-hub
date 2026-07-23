package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.TerminalStatus;
import java.time.LocalDate;

public class TerminalInventoryResponseDTO {
    private Long id;
    private String terminalNumber;
    private String terminalSerialNumber;
    private String manufacturer;
    private String model;
    private LocalDate purchasedDate;
    private Long terminalTypeId;
    private String terminalTypeName;
    private TerminalStatus status;
}

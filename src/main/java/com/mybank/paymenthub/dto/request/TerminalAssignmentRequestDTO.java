package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.TerminalAssignmentAction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalAssignmentRequestDTO {
    // Assignment Reference
    private Long terminalAssignmentId;

    // Terminal Information
    private Long terminalInventoryId;
    private String terminalNumber;
    private String serialNumber;

    //Outlet Information
    private Long merchantOutletId;
    private String merchantOutletName;

    private LocalDate assignedDate;
    private LocalDate returnedDate;
    private TerminalAssignmentAction action;

    private String remarks;

}

package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.TerminalAssignmentAction;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminalAssignmentHistoryResponseDTO {


    // History ID
    private Long id;


    // Original Assignment Reference
    private Long terminalAssignmentId;


    // Terminal Information
    private Long terminalInventoryId;
    private String terminalNumber;
    private String serialNumber;


    // Outlet Information
    private Long merchantOutletId;
    private String merchantOutletName;


    // Assignment Period
    private LocalDate assignedDate;
    private LocalDate returnedDate;


    // History Action
    private TerminalAssignmentAction action;


    private String remarks;

}
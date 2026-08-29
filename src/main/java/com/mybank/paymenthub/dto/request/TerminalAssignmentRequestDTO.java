package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class TerminalAssignmentRequestDTO {

    @NotNull(
            message = "Terminal Inventory is Required"
    )
    private Long terminalInventoryId;

    @NotNull(
            message = "Merchant Outlet is Required"
    )
    private Long merchantOutletId;

    @NotNull(
            message = "Terminal Assignment Date is Required"
    )

    private LocalDate terminalAssignmentDate;

    private LocalDate terminalInstallationDate;

    private LocalDate terminalActivationDate;

}

package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.TerminalAssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalAssignmentResponseDTO {

        private Long id;
        private String terminalAssignmentNumber;
        private Long terminalInventoryId;
        private String terminalNumber;
        private String terminalSerialNumber;
        private String model;
        private Long merchantOutletId;
        private String merchantOutletName;
        private LocalDate terminalAssignmentDate;
        private LocalDate terminalInstallationDate;
        private LocalDate terminalActivationDate;
        private LocalDate terminalTerminationDate;
        private LocalDateTime lastTransactionDate;
        private TerminalAssignmentStatus status;
}


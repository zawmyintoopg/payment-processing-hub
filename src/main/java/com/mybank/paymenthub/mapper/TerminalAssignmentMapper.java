package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.TerminalAssignmentRequestDTO;
import com.mybank.paymenthub.dto.response.TerminalAssignmentResponseDTO;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.entity.TerminalAssignment;
import com.mybank.paymenthub.entity.TerminalInventory;
import org.springframework.stereotype.Component;

/**
 * Converts for between Terminal Assignment Entity and DTOs
 */
@Component
public class TerminalAssignmentMapper {

    public TerminalAssignment
            toEntity(TerminalAssignmentRequestDTO request){

        TerminalAssignment entity = new TerminalAssignment();

        entity.setTerminalAssignmentDate(request.getTerminalAssignmentDate());

        entity.setTerminalInstallationDate(request.getTerminalInstallationDate());

        entity.setTerminalActivationDate(request.getTerminalActivationDate());

        return entity;
    }

    public TerminalAssignmentResponseDTO
            toResponse(TerminalAssignment entity){
        TerminalAssignmentResponseDTO response
                = new TerminalAssignmentResponseDTO();

        response.setId(entity.getId());
        response.setTerminalAssignmentNumber(
                entity.getTerminalAssignmentNumber()
        );
        TerminalInventory terminalInventory =
                entity.getTerminalInventory();
        if(terminalInventory != null){
            response.setTerminalInventoryId(terminalInventory.getId());
            response.setTerminalNumber(terminalInventory.getTerminalNumber());
            response.setTerminalSerialNumber(terminalInventory.getTerminalSerialNumber());
            response.setModel(terminalInventory.getModel());
        }
        MerchantOutlet merchantOutlet =
                entity.getMerchantOutlet();
        if(entity.getMerchantOutlet() != null){
            response.setMerchantOutletId(merchantOutlet.getId());
            response.setMerchantOutletName(merchantOutlet.getOutletName());
        }
        if(entity.getTerminalAssignmentDate() != null){
            response.setTerminalAssignmentDate(entity.getTerminalAssignmentDate());
        }

        if(entity.getTerminalActivationDate() != null){
            response.setTerminalActivationDate(entity.getTerminalActivationDate());
        }

        if(entity.getTerminalInstallationDate() != null){
            response.setTerminalInstallationDate(entity.getTerminalInstallationDate());
        }

        if(entity.getTerminalAssignmentDate() != null){
            response.setTerminalTerminationDate(entity.getTerminalTerminationDate());
        }

        if(entity.getLastTransactionDate() != null){
            response.setLastTransactionDate(entity.getLastTransactionDate());
        }

        if(entity.getStatus() != null){
            response.setStatus(entity.getStatus());
        }

        return response;
    }


    public void updateEntity(
            TerminalAssignmentRequestDTO requestDTO,
            TerminalAssignment entity
    ){

        entity.setTerminalAssignmentDate(
                requestDTO.getTerminalAssignmentDate()
        );

        entity.setTerminalInstallationDate(
                requestDTO.getTerminalInstallationDate()
        );

        entity.setTerminalActivationDate(
                requestDTO.getTerminalActivationDate()
        );
    }
}

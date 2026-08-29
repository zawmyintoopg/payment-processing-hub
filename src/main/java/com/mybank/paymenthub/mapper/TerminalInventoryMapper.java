package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.TerminalInventoryRequestDTO;
import com.mybank.paymenthub.dto.response.TerminalInventoryResponse;
import com.mybank.paymenthub.entity.TerminalInventory;
import com.mybank.paymenthub.entity.TerminalType;
import org.springframework.stereotype.Component;

@Component
public class TerminalInventoryMapper {

    public TerminalInventory toEntity(TerminalInventoryRequestDTO requestDTO){

        TerminalInventory entity = new TerminalInventory();

        entity.setTerminalSerialNumber(requestDTO.getTerminalSerialNumber());
        entity.setManufacturer(requestDTO.getManufacturer());
        entity.setModel(requestDTO.getModel());
        entity.setPurchasedDate(requestDTO.getPurchasedDate());
        return entity;
    }

    public TerminalInventoryResponse toResponse(
            TerminalInventory entity
    ){
        TerminalInventoryResponse response =
                new TerminalInventoryResponse();

        response.setId(entity.getId());

        if(entity.getTerminalNumber() != null){
            response.setTerminalNumber(
                    entity.getTerminalNumber()
            );
        }
        if(entity.getTerminalSerialNumber() != null){
            response.setTerminalSerialNumber(
                    entity.getTerminalSerialNumber()
            );
        }
        if(entity.getManufacturer() != null){
            response.setManufacturer(
                    entity.getManufacturer()
            );
        }
        if(entity.getPurchasedDate() != null){
            response.setPurchasedDate(
                    entity.getPurchasedDate()
            );
        }
        TerminalType terminalType = entity.getTerminalType();

        if(terminalType != null){
            response.setTerminalTypeId(terminalType.getId());
            response.setTerminalTypeName(terminalType.getTypeName());
        }
        if(entity.getStatus() != null){
            response.setStatus(
                    entity.getStatus()
            );
        }

        return response;
    }

    public void updateEntity(TerminalInventoryRequestDTO requestDTO,
                             TerminalInventory entity){

        entity.setTerminalSerialNumber(
                requestDTO.getTerminalSerialNumber()
        );
        entity.setManufacturer(
                requestDTO.getManufacturer()
        );
        entity.setModel(
                requestDTO.getModel()
        );
        entity.setPurchasedDate(
                requestDTO.getPurchasedDate()
        );
    }
}

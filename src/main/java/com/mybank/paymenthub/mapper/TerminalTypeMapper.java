package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.TerminalTypeRequest;
import com.mybank.paymenthub.dto.response.TerminalTypeResponse;
import com.mybank.paymenthub.entity.TerminalType;
import org.springframework.stereotype.Component;

@Component
public class TerminalTypeMapper {

    public TerminalType toEntity(
            TerminalTypeRequest request
    ){
        TerminalType terminalType = new TerminalType();

       terminalType.setTypeCode(
               terminalType.getTypeCode()
       );
       terminalType.setTypeName(
               terminalType.getTypeName()
       );
       terminalType.setStatus(
               terminalType.getStatus()
       );

       return terminalType;
    }
    public TerminalTypeResponse toResponse(
            TerminalType terminalType
    ){
        TerminalTypeResponse terminalTypeResponse =
                new TerminalTypeResponse();

        terminalTypeResponse.setId(
                terminalType.getId()
        );
        terminalTypeResponse.setTypeCode(
                terminalType.getTypeCode()
        );
        terminalTypeResponse.setTypeName(
                terminalType.getTypeName()
        );

        return terminalTypeResponse;
    }
    public TerminalType updateEntity(
            TerminalType terminalType,
            TerminalTypeRequest terminalTypeRequest
    ){

        terminalType.setTypeCode(
                terminalTypeRequest.getTypeCode()
        );
        terminalType.setTypeName(
                terminalTypeRequest.getTypeName()
        );
        return terminalType;
    }
}

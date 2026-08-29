package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.TerminalTypeRequest;
import com.mybank.paymenthub.dto.response.TerminalTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TerminalTypeService {

    TerminalTypeResponse createTerminalType(
            TerminalTypeRequest request
    );

    TerminalTypeResponse updateTerminalType(
            Long id,
            TerminalTypeRequest request
    );

    Page<TerminalTypeResponse> getAllTerminalTypes(
            String search,
            Pageable pageable
    );

    TerminalTypeResponse getTerminalTypeById(
            Long id
    );

    void deActivateTerminalType(
            Long id
    );

    void activateTerminalType(
            Long id
    );
}
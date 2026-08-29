package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.TerminalAssignmentRequestDTO;
import com.mybank.paymenthub.dto.response.TerminalAssignmentResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface
     TerminalAssignmentService {

    TerminalAssignmentResponseDTO create(
            TerminalAssignmentRequestDTO requestDTO
    );
    TerminalAssignmentResponseDTO update(
            Long id,
            TerminalAssignmentRequestDTO request
    );

    Page<TerminalAssignmentResponseDTO> getAll(
            String search,
            Pageable pageable
    );

    TerminalAssignmentResponseDTO getById(
            Long id
    );

    void deActivate(Long id);

    void activate(Long id);

}

package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.TerminalTypeRequest;
import com.mybank.paymenthub.dto.response.TerminalTypeResponse;
import com.mybank.paymenthub.entity.TerminalType;
import com.mybank.paymenthub.enums.Status;
import com.mybank.paymenthub.repository.TerminalTypeRepository;
import com.mybank.paymenthub.service.TerminalTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TerminalTypeServiceImpl implements TerminalTypeService {

    private final TerminalTypeRepository terminalTypeRepository;

    // =========================
    // CREATE
    // =========================
    @Override
    public TerminalTypeResponse createTerminalType(
            TerminalTypeRequest request
    ) {

        // Duplicate check
        if (terminalTypeRepository.existsByTypeCodeAndTypeName(
                request.getTypeCode(),
                request.getTypeName()
        )) {
            throw new RuntimeException(
                    "Terminal type already exists with code: "
                            + request.getTypeCode()
                            + " and name: "
                            + request.getTypeName()
            );
        }

        TerminalType terminalType = new TerminalType();

        terminalType.setTypeCode(request.getTypeCode());
        terminalType.setTypeName(request.getTypeName());
        terminalType.setStatus(Status.ACTIVE);

        TerminalType saved =
                terminalTypeRepository.save(terminalType);

        return mapToResponse(saved);
    }

    // =========================
    // UPDATE
    // =========================
    @Override
    public TerminalTypeResponse updateTerminalType(
            Long id,
            TerminalTypeRequest request
    ) {

        TerminalType terminalType =
                terminalTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Terminal type not found: " + id
                                )
                        );

        // Duplicate check excluding current record
        if (terminalTypeRepository
                .existsByTypeCodeAndTypeNameAndIdNot(
                        request.getTypeCode(),
                        request.getTypeName(),
                        id
                )) {

            throw new RuntimeException(
                    "Another terminal type already exists with code: "
                            + request.getTypeCode()
                            + " and name: "
                            + request.getTypeName()
            );
        }

        terminalType.setTypeCode(request.getTypeCode());
        terminalType.setTypeName(request.getTypeName());

        TerminalType updated =
                terminalTypeRepository.save(terminalType);

        return mapToResponse(updated);
    }

    // =========================
    // GET ALL
    // =========================
    @Override
    @Transactional(readOnly = true)
    public Page<TerminalTypeResponse> getAllTerminalTypes(
            String search,
            Pageable pageable
    ) {

        Page<TerminalType> terminalTypes;

        if (search == null || search.isBlank()) {

            terminalTypes =
                    terminalTypeRepository.findAll(pageable);

        } else {

            terminalTypes =
                    terminalTypeRepository
                            .findByTypeCodeContainingIgnoreCaseOrTypeNameContainingIgnoreCase(
                                    search,
                                    search,
                                    pageable
                            );
        }

        return terminalTypes.map(this::mapToResponse);
    }

    // =========================
    // GET BY ID
    // =========================
    @Override
    @Transactional(readOnly = true)
    public TerminalTypeResponse getTerminalTypeById(
            Long id
    ) {

        TerminalType terminalType =
                terminalTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Terminal type not found: " + id
                                )
                        );

        return mapToResponse(terminalType);
    }

    // =========================
    // DEACTIVATE
    // =========================
    @Override
    public void deActivateTerminalType(
            Long id
    ) {

        TerminalType terminalType =
                terminalTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Terminal type not found: " + id
                                )
                        );

        terminalType.setStatus(Status.INACTIVE);

        terminalTypeRepository.save(terminalType);
    }

    // =========================
    // ACTIVATE
    // =========================
    @Override
    public void activateTerminalType(
            Long id
    ) {

        TerminalType terminalType =
                terminalTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Terminal type not found: " + id
                                )
                        );

        terminalType.setStatus(Status.ACTIVE);

        terminalTypeRepository.save(terminalType);
    }

    // =========================
    // MAPPER
    // =========================
    private TerminalTypeResponse mapToResponse(
            TerminalType terminalType
    ) {

        TerminalTypeResponse response =
                new TerminalTypeResponse();

        response.setId(terminalType.getId());
        response.setTypeCode(terminalType.getTypeCode());
        response.setTypeName(terminalType.getTypeName());
        response.setStatus(terminalType.getStatus());

        return response;
    }
}
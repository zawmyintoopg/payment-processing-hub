package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.TerminalInventoryRequestDTO;
import com.mybank.paymenthub.dto.response.TerminalInventoryResponse;
import com.mybank.paymenthub.entity.TerminalInventory;
import com.mybank.paymenthub.entity.TerminalType;
import com.mybank.paymenthub.enums.TerminalStatus;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.TerminalInventoryMapper;
import com.mybank.paymenthub.repository.TerminalInventoryRepository;
import com.mybank.paymenthub.repository.TerminalTypeRepository;
import com.mybank.paymenthub.service.NumberSequenceService;
import com.mybank.paymenthub.service.TerminalInventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TerminalInventoryServiceImpl
        implements TerminalInventoryService {

    private final TerminalInventoryRepository terminalInventoryRepository;
    private final TerminalInventoryMapper terminalInventoryMapper;
    private final TerminalTypeRepository terminalTypeRepository;
    private final NumberSequenceService numberSequenceService;
    @Override
    public TerminalInventoryResponse create(
            TerminalInventoryRequestDTO requestDTO
    ){
        TerminalType terminalType =
                findByTerminalTypeId(requestDTO.getTerminalTypeId());

        String terminalNumber =
                numberSequenceService.generateTerminalNumber();
        TerminalInventory entity =
                terminalInventoryMapper.toEntity(requestDTO);
        entity.setTerminalNumber(
                terminalNumber
        );
        entity.setTerminalType(
                terminalType
        );
        entity.setStatus(TerminalStatus.ACTIVE);

        TerminalInventory saved
                = terminalInventoryRepository.save(entity);

        return terminalInventoryMapper.toResponse(saved);
    }
    @Override
    public TerminalInventoryResponse update(
            TerminalInventoryRequestDTO requestDTO, Long id
    ){
        TerminalInventory terminalInventory = getTerminalInventory(id);
        TerminalType terminalType = findByTerminalTypeId(
                requestDTO.getTerminalTypeId()
        );
        terminalInventoryMapper.updateEntity(requestDTO,terminalInventory);
        terminalInventory.setTerminalType(
                terminalType
        );

        return terminalInventoryMapper.toResponse(terminalInventory);

    }
    @Override
    public Page<TerminalInventoryResponse> getAll(
            String search,
            Pageable pageable
    ){
        Page<TerminalInventory> terminalInventoryPage;

        if(search == null || search.isBlank()){

            terminalInventoryPage =
                    terminalInventoryRepository.findAll(pageable);
        }else{
            terminalInventoryPage =
                    terminalInventoryRepository
                            .searchTerminalNumberOrTerminalSerialNumber(search,pageable);
        }

        return terminalInventoryPage.map(
                terminalInventoryMapper::toResponse
        );
    }
    @Override
    public TerminalInventoryResponse getById(
            Long id
    ){
        TerminalInventory terminalInventory =
                getTerminalInventory(id);

        return terminalInventoryMapper
                .toResponse(terminalInventory);

    }
    @Override
    public void deActivate(
            Long id
    ){
        TerminalInventory terminalInventory =
                getTerminalInventory(id);
        if(terminalInventory.getStatus() == TerminalStatus.INACTIVE){
            throw new BusinessException(
                    "Terminal Status is Already InActive"
            );
        }
        terminalInventory.setStatus(TerminalStatus.INACTIVE);

    }
    @Override
    public void activate(
            Long id
    ){
        TerminalInventory terminalInventory =
                getTerminalInventory(id);
        if(terminalInventory.getStatus() == TerminalStatus.ACTIVE){
            throw new BusinessException(
                    "Terminal Status is Already Active"
            );
        }
        terminalInventory.setStatus(TerminalStatus.ACTIVE);

    }
    private TerminalInventory getTerminalInventory(Long id){

        return terminalInventoryRepository
                    .findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException(
                            "Terminal Inventory Not Found",
                            "id",
                            id
                    )
                );
    }
    private TerminalType findByTerminalTypeId(Long id){

        return terminalTypeRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(
                                "Terminal Type Not Found",
                                "id",
                                id
                        )
                );
    }
}

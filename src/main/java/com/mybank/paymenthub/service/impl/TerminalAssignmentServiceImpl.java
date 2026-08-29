package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.TerminalAssignmentRequestDTO;
import com.mybank.paymenthub.dto.response.TerminalAssignmentResponseDTO;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.entity.TerminalAssignment;
import com.mybank.paymenthub.entity.TerminalInventory;
import com.mybank.paymenthub.enums.TerminalAssignmentStatus;
import com.mybank.paymenthub.enums.TerminalStatus;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.TerminalAssignmentMapper;
import com.mybank.paymenthub.repository.MerchantOutletRepository;
import com.mybank.paymenthub.repository.TerminalAssignmentRepository;
import com.mybank.paymenthub.repository.TerminalInventoryRepository;
import com.mybank.paymenthub.service.NumberSequenceService;
import com.mybank.paymenthub.service.TerminalAssignmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TerminalAssignmentServiceImpl
        implements TerminalAssignmentService {

    private final TerminalAssignmentMapper terminalAssignmentMapper;
    private final NumberSequenceService numberSequenceService;
    private final TerminalAssignmentRepository terminalAssignmentRepository;
    private final TerminalInventoryRepository terminalInventoryRepository;
    private final MerchantOutletRepository merchantOutletRepository;
    @Override
    public TerminalAssignmentResponseDTO create(
            TerminalAssignmentRequestDTO requestDTO
    ){
        TerminalInventory terminalInventory =
                getTerminalInventory(requestDTO.getTerminalInventoryId());
        checkTerminalAvailability(terminalInventory);
        checkDuplicateTerminalAssignment(requestDTO);

        MerchantOutlet merchantOutlet =
                getMerchantOutlet(requestDTO.getMerchantOutletId());



        String terminalAssignmentNumber =
                numberSequenceService.generateTerminalAssignmentNumber();

        TerminalAssignment terminalAssignment =
                terminalAssignmentMapper.toEntity(requestDTO);
        terminalAssignment.setTerminalAssignmentNumber(
                terminalAssignmentNumber
        );
        terminalAssignment.setMerchantOutlet(
                merchantOutlet
        );
        terminalAssignment.setTerminalInventory(
                terminalInventory
        );
        terminalAssignment.setStatus(
                TerminalAssignmentStatus.ASSIGNED
        );

        TerminalAssignment saved =
                terminalAssignmentRepository.save(terminalAssignment);

        return terminalAssignmentMapper.toResponse(saved);
    }
    @Override
    public TerminalAssignmentResponseDTO update(
            Long id,
            TerminalAssignmentRequestDTO requestDTO
    ){
        TerminalAssignment assignment =
                findTerminalAssignment(id);
        TerminalInventory terminalInventory =
                getTerminalInventory(requestDTO.getTerminalInventoryId());

        checkTerminalAvailability(terminalInventory);
        checkDuplicateTerminalAssignmentForUpdate(
                requestDTO.getMerchantOutletId(),
                requestDTO.getTerminalInventoryId(),
                id
        );
        MerchantOutlet merchantOutlet =
                getMerchantOutlet(requestDTO.getMerchantOutletId());


        terminalAssignmentMapper.updateEntity(requestDTO,assignment);
        assignment.setMerchantOutlet(merchantOutlet);
        assignment.setTerminalInventory(terminalInventory);

        return terminalAssignmentMapper.toResponse(assignment);
    }
    @Override
    public Page<TerminalAssignmentResponseDTO> getAll(
            String search,
            Pageable pageable
    ){
        Page<TerminalAssignment> terminalAssignments;

        if( search == null || search.isBlank()){
            terminalAssignments =
                    terminalAssignmentRepository.findAll(
                            pageable
                    );
        }else{
            terminalAssignments =
                    terminalAssignmentRepository
                            .searchTerminalAssignmentNumber(
                                    search,pageable
                            );
        }
        return terminalAssignments.map(
                terminalAssignmentMapper::toResponse
        );
    }
    @Override
    public TerminalAssignmentResponseDTO getById(
            Long id
    ){
        TerminalAssignment terminalAssignment = findTerminalAssignment(id);
        return terminalAssignmentMapper.toResponse(terminalAssignment);

    }
    @Override
    public void deActivate(Long id){
        TerminalAssignment terminalAssignment = findTerminalAssignment(id);

        terminalAssignment.setStatus(TerminalAssignmentStatus.TERMINATED);
    }
    @Override
    public void activate(Long id){
        TerminalAssignment terminalAssignment = findTerminalAssignment(id);

        terminalAssignment.setStatus(TerminalAssignmentStatus.ACTIVE);
    }

    private void checkDuplicateTerminalAssignment(
           TerminalAssignmentRequestDTO requestDTO
    ){
        if
        (
                requestDTO.getMerchantOutletId() != null &&
                requestDTO.getTerminalInventoryId() != null &&
                terminalAssignmentRepository.existsByMerchantOutlet_IdAndTerminalInventory_Id
                        (requestDTO.getMerchantOutletId(),
                        requestDTO.getTerminalInventoryId()
                 )
        ) {

            throw new DuplicateException(
                    "Terminal already Exist in this Outlet Merchant"
            );
        }

    }
    private void checkDuplicateTerminalAssignmentForUpdate(
            Long merchantOutletId,
            Long terminalInventoryId,
            Long id
    ){
        if
        (
            terminalAssignmentRepository.existsByMerchantOutlet_IdAndTerminalInventory_IdAndIdNot(
                    merchantOutletId,
                    terminalInventoryId,
                    id
                    )
        ) {
            throw new DuplicateException(
                    "Terminal already Exist in this Outlet Merchant"
            );
        }

    }
    private void checkTerminalAvailability(TerminalInventory terminalInventory){


        if (terminalInventory.getStatus() != TerminalStatus.ACTIVE){

            throw new BusinessException("Terminal is not active and cannot be assigned");
        }
    }
    private TerminalInventory getTerminalInventory(Long id){

        return terminalInventoryRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException(
                                "Terminal Inventory Not Found",
                                "id",
                                 id
                        )
                );
    }

    private MerchantOutlet getMerchantOutlet(Long id){

        return merchantOutletRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Merchant Outlet Not Found",
                                "id",
                                id
                        )
                );
    }
    private TerminalAssignment findTerminalAssignment(Long id){

        return terminalAssignmentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Terminal Assignment Not Found",
                                "id",
                                id
                        )
                );
    }

}

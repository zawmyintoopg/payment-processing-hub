package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantCategory;
import com.mybank.paymenthub.entity.MerchantOwner;
import com.mybank.paymenthub.entity.MerchantSegment;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantMapper;
import com.mybank.paymenthub.repository.MerchantCategoryRepository;
import com.mybank.paymenthub.repository.MerchantOwnerRepository;
import com.mybank.paymenthub.repository.MerchantRepository;
import com.mybank.paymenthub.repository.MerchantSegmentRepository;
import com.mybank.paymenthub.service.MerchantService;
import com.mybank.paymenthub.service.NumberSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
/**
 * Service implementation for Merchant management.
 *
 * Responsibilities:
 * - Create merchants
 * - Update merchant information
 * - Retrieve merchant details
 * - Manage merchant activation status
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;
    private final NumberSequenceService numberSequenceService;

    private final MerchantOwnerRepository merchantOwnerRepository;
    private final MerchantSegmentRepository merchantSegmentRepository;
    private final MerchantCategoryRepository merchantCategoryRepository;

    @Override
    public MerchantResponseDTO createMerchant(
            MerchantRequestDTO requestDTO
    ){

        checkDuplicateForCreate(requestDTO);
        MerchantOwner merchantOwner =
                merchantOwnerRepository.findById(requestDTO.getMerchantOwnerId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Merchant Owner Not Found",
                                "id",
                                requestDTO.getMerchantOwnerId())
                        );
        MerchantSegment merchantSegment =
                merchantSegmentRepository.findById(requestDTO.getMerchantSegmentId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Merchant Segment Not Found",
                                "id",
                                requestDTO.getMerchantSegmentId())
                        );
        MerchantCategory merchantCategory =
                merchantCategoryRepository.findById(requestDTO.getMerchantCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Merchant Category Not found",
                                "id",
                                requestDTO.getMerchantCategoryId())
                      );
        Merchant merchant =
                merchantMapper.toEntity(requestDTO);
        String merchantNumber =
                numberSequenceService.generateMerchantNumber();

        merchant.setMerchantNumber(merchantNumber);
        merchant.setMerchantOwner(merchantOwner);
        merchant.setMerchantSegment(merchantSegment);
        merchant.setMerchantCategory(merchantCategory);

        Merchant saved =
                merchantRepository.save(merchant);

        return merchantMapper.toResponse(saved);
    }
    @Override
    public MerchantResponseDTO updateMerchant(
            Long id,MerchantRequestDTO requestDTO
    ){
        Merchant merchant = findMerchantById(id);
        checkDuplicateForUpdate(id,requestDTO);
        merchantMapper.updateEntity(requestDTO,merchant);
        merchant.setMerchantOwner(
                findMerchantOwnerById(requestDTO.getMerchantOwnerId())
        );
        merchant.setMerchantSegment(
               findMerchantSegmentById(requestDTO.getMerchantSegmentId())
         );
        merchant.setMerchantCategory(
                findMerchantCategoryById(requestDTO.getMerchantCategoryId())
        );
        Merchant updated =
                merchantRepository.save(merchant);
        return merchantMapper.toResponse(updated);
    }
    @Override
    public Page<MerchantResponseDTO> getAllMerchants(
            String search, Pageable pageable
    ) {
        Page<Merchant> merchants;
        if(search == null || search.isBlank()){
            merchants =
                    merchantRepository.findAllWithDetails(pageable);
        }
        else{
            merchants =
                    merchantRepository.searchMerchants(search,pageable);
        }
        return merchants.map(
                merchantMapper::toResponse
        );
    }
    @Override
    public MerchantResponseDTO getMerchantById(Long id){
        Merchant merchant = findMerchantById(id);
        return merchantMapper.toResponse(merchant);
    }
    @Override
    public void activateMerchant(Long id){
        Merchant merchant = findMerchantById(id);
        if (merchant.getStatus() == MerchantStatus.ACTIVE){
            throw new BusinessException(
                    "Merchant is Already Active"
            );
        }
        merchant.setStatus(MerchantStatus.ACTIVE);
        merchantRepository.save(merchant);
    }
    @Override
    public void deactivateMerchant(Long id){
        Merchant merchant = findMerchantById(id);
        if (merchant.getStatus() == MerchantStatus.INACTIVE){
            throw new BusinessException(
                    "Merchant is Already InActive"
            );
        }
        merchant.setStatus(MerchantStatus.INACTIVE);
        merchantRepository.save(merchant);
    }
    private void checkDuplicateForCreate(MerchantRequestDTO requestDTO) {

        if (requestDTO.getMerchantName() != null &&
                merchantRepository.existsByMerchantName(requestDTO.getMerchantName())) {
            throw new DuplicateException("Merchant Name Already Exist");
        }
    }
    private Merchant findMerchantById(Long id){

        return merchantRepository.findByIdWithDetails(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Merchant Not Found",
                                "id",
                                id
                        )
                );
    }
    private void checkDuplicateForUpdate(
            Long id,
            MerchantRequestDTO requestDTO
    ){

        if(requestDTO.getMerchantName() != null &&
                merchantRepository.existsByMerchantNameAndIdNot(
                        requestDTO.getMerchantName(),
                        id
                )
        ){
            throw new DuplicateException(
                    "Merchant Name Already Exist"
            );
        }
    }

    private MerchantOwner findMerchantOwnerById(Long id){
        return merchantOwnerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Merchant Owner Not Found",
                        "id",
                        id
                )
        );
    }

    private MerchantSegment findMerchantSegmentById(Long id){
        return merchantSegmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Merchant Segment Not Found",
                        "id",
                        id
                )
        );
    }

    private MerchantCategory findMerchantCategoryById(Long id){
        return merchantCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Merchant Category Not Found",
                        "id",
                        id
                )
         );
    }
}

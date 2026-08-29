package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantOwnerRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOwnerResponseDTO;
import com.mybank.paymenthub.entity.MerchantOwner;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantOwnerMapper;
import com.mybank.paymenthub.repository.MerchantOwnerRepository;
import com.mybank.paymenthub.service.MerchantOwnerService;
import com.mybank.paymenthub.service.NumberSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
/**
 * Service implementation for Merchant Owner management.
 *
 * Responsibilities:
 * - Create merchant owners
 * - Update merchant owner information
 * - Retrieve merchant owner details
 * - Manage merchant owner activation status
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MerchantOwnerServiceImpl implements MerchantOwnerService {

    private final MerchantOwnerRepository merchantOwnerRepository;
    private final MerchantOwnerMapper merchantOwnerMapper;
    private final NumberSequenceService numberSequenceService;
    @Override
    public MerchantOwnerResponseDTO createMerchantOwner(
            MerchantOwnerRequestDTO requestDTO
    ){
        validateDuplicate(requestDTO);
        MerchantOwner merchantOwner =
                merchantOwnerMapper.toEntity(requestDTO);
        String ownerNumber =
                numberSequenceService.generateOwnerNumber();
        merchantOwner.setOwnerNumber(ownerNumber);
        merchantOwner.setStatus(MerchantStatus.ACTIVE);
        MerchantOwner savedMerchantOwner =
                merchantOwnerRepository.save(merchantOwner);

        return merchantOwnerMapper.toResponse(savedMerchantOwner);
    }
    @Override
    public MerchantOwnerResponseDTO updateMerchantOwner(
            Long id,
            MerchantOwnerRequestDTO requestDTO
    ){

        MerchantOwner merchantOwner = findMerchantOwnerById(id);

        checkDuplicateForUpdate(id, requestDTO);

        merchantOwnerMapper.updateEntity(
                requestDTO,merchantOwner
        );

        MerchantOwner updatedMerchantOwner =
                merchantOwnerRepository.save(merchantOwner);

        return merchantOwnerMapper.toResponse(updatedMerchantOwner);
    }
    @Override
    public Page<MerchantOwnerResponseDTO> getAllMerchantOwners(
            String search,
            Pageable pageable
    ) {

        Page<MerchantOwner> merchantOwners;

        if(search == null || search.isBlank()){
            merchantOwners = merchantOwnerRepository.findAll(pageable);
        }
        else{
            merchantOwners   = merchantOwnerRepository
                    .searchMerchantOwners(search,pageable);
        }

        return merchantOwners.map(
                merchantOwnerMapper::toResponse
        );
    }
    @Override
    public MerchantOwnerResponseDTO getMerchantOwnerById(Long id){
       MerchantOwner merchantOwner = findMerchantOwnerById(id);

       return merchantOwnerMapper.toResponse(merchantOwner);
    }
    @Override
    public void activateMerchantOwner(Long id){
        MerchantOwner merchantOwner = findMerchantOwnerById(id);
        merchantOwner.setStatus(MerchantStatus.ACTIVE);
        merchantOwnerRepository.save(merchantOwner);
    }
    @Override
    public void deactivateMerchantOwner(Long id){
        MerchantOwner merchantOwner = findMerchantOwnerById(id);
        merchantOwner.setStatus(MerchantStatus.INACTIVE);
        merchantOwnerRepository.save(merchantOwner);
    }
    private void validateDuplicate(MerchantOwnerRequestDTO requestDTO) {

        if (requestDTO.getRegistrationNo() != null &&
                merchantOwnerRepository.existsByRegistrationNo(
                        requestDTO.getRegistrationNo())
        ) {
            throw new DuplicateException("Owner Registration number already exist !");
        }

        if (requestDTO.getOwnerName() != null &&
                merchantOwnerRepository.existsByOwnerName(requestDTO.getOwnerName())) {
            throw new DuplicateException("Owner Name Already Exist !");
        }

        if (requestDTO.getEmail() != null &&
                merchantOwnerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateException("Email Already Exist !");
        }
    }
    private MerchantOwner findMerchantOwnerById(Long id){

        return merchantOwnerRepository.findById(id).
        orElseThrow(() -> new ResourceNotFoundException(
                        "MerchantOwner Not Found",
                        "id",
                        id
                )
        );
    }
    private void checkDuplicateForUpdate(
            Long id,
            MerchantOwnerRequestDTO requestDTO
    ){

        if(requestDTO.getRegistrationNo() != null &&
                merchantOwnerRepository.existsByRegistrationNoAndIdNot(
                        requestDTO.getRegistrationNo(),
                        id
                )
        ){
            throw new DuplicateException(
                    "Owner Registration Number Already Exist!"
            );
        }

        if(requestDTO.getOwnerName() != null &&
                merchantOwnerRepository.existsByOwnerNameAndIdNot(
                        requestDTO.getOwnerName(),
                        id
                )
        ){
            throw new DuplicateException(
                    "Owner Name Already Exist!"
            );
        }

        if(requestDTO.getEmail() != null &&
                merchantOwnerRepository.existsByEmailAndIdNot(
                        requestDTO.getEmail(),
                        id
                )
        ){
            throw new DuplicateException(
                    "Email Already Exist!"
            );
        }
    }
}

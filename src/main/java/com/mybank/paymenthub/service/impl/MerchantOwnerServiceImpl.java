package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantOwnerRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOwnerResponseDTO;
import com.mybank.paymenthub.entity.MerchantOwner;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.enums.Status;
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


@Service
@RequiredArgsConstructor
@Transactional
public class MerchantOwnerServiceImpl implements MerchantOwnerService {

    private final MerchantOwnerRepository merchantOwnerRepository;
    private final MerchantOwnerMapper merchantOwnerMapper;
    private final NumberSequenceService numberSequenceService;
    private void validateDuplicate(MerchantOwnerRequestDTO requestDTO){

        if(requestDTO.getRegistrationNo() != null &&
                merchantOwnerRepository.existsByRegistrationNo(requestDTO.getRegistrationNo())){
            throw new DuplicateException("Owner Registration No is Already Exist !");
        }

        if(requestDTO.getOwnerName() != null &&
                merchantOwnerRepository.existsByOwnerName(requestDTO.getOwnerName())){
            throw new DuplicateException("Owner Name Already Exist !");
        }

        if(requestDTO.getEmail() != null &&
                merchantOwnerRepository.existsByEmail(requestDTO.getEmail())){
            throw new DuplicateException("Email Already Exist !");
        }

    }

    // CREATE MERCHANT OWNER
    @Override
    public MerchantOwnerResponseDTO createMerchantOwner(MerchantOwnerRequestDTO requestDTO){

        validateDuplicate(requestDTO);
        MerchantOwner merchantOwner = new MerchantOwner();
        String getOwnerNumber =
                numberSequenceService.generateOwnerNumber();

        merchantOwner.setOwnerNumber(getOwnerNumber);
        merchantOwner.setOwnerName(requestDTO.getOwnerName());
        merchantOwner.setRegistrationNo(requestDTO.getRegistrationNo());
        merchantOwner.setOwnerType(requestDTO.getOwnerType());
        merchantOwner.setPhone(requestDTO.getPhone());
        merchantOwner.setEmail(requestDTO.getEmail());
        MerchantOwner savedMerchantOwner =  merchantOwnerRepository.save(merchantOwner);

        return merchantOwnerMapper.toResponse(savedMerchantOwner);
    }

    // UPDATE MERCHANT OWNER
    @Override
    public MerchantOwnerResponseDTO updateMerchantOwner(Long id , MerchantOwnerRequestDTO requestDTO){
        //search id update merchants
        MerchantOwner merchantOwner = merchantOwnerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ID NOT FOUND","id",id));

        if(requestDTO.getOwnerName() != null &&
                merchantOwnerRepository.existsByOwnerNameAndIdNot(requestDTO.getOwnerName(),id)){
            throw new DuplicateException("Owner Name is Already Exist ! in "+id);
        }

        if(requestDTO.getRegistrationNo() != null &&
                merchantOwnerRepository.existsByRegistrationNoAndIdNot(requestDTO.getRegistrationNo(),id)){
            throw new DuplicateException("Registration Number is Already Exist !");
        }

        if(requestDTO.getEmail() != null &&
                merchantOwnerRepository.existsByEmailAndIdNot(requestDTO.getEmail(),id)){
            throw new DuplicateException("Email Already Exist !");
        }

        merchantOwner.setOwnerName(requestDTO.getOwnerName());
        merchantOwner.setOwnerType(requestDTO.getOwnerType());
        merchantOwner.setRegistrationNo(requestDTO.getRegistrationNo());
        merchantOwner.setPhone(requestDTO.getPhone());
        merchantOwner.setEmail(requestDTO.getEmail());
        merchantOwner.setStatus(requestDTO.getStatus());

        MerchantOwner updatedMerchantOwner = merchantOwnerRepository.save(merchantOwner);
        return merchantOwnerMapper.toResponse(updatedMerchantOwner);
    }
    //GET ALL MERCHANTS
  /*  @Override
    public Page<MerchantOwnerResponseDTO> getAllMerchantOwners(Pageable pageable){
        // Get Merchant List from Table
        Page<MerchantOwner> merchantOwners =
                merchantOwnerRepository.findAll(pageable);

        return merchantOwners.
                map(merchantOwnerMapper::toResponse);
    } */
    @Override
    public Page<MerchantOwnerResponseDTO> getAllMerchantOwners(Pageable pageable) {

        System.out.println("Before findAll");

        Page<MerchantOwner> merchantOwners = merchantOwnerRepository.findAll(pageable);

        System.out.println("After findAll: " + merchantOwners.getTotalElements());

        return merchantOwners.map(merchantOwnerMapper::toResponse);
    }
    @Override
    public MerchantOwnerResponseDTO getMerchantOwnerById(Long id){
       // Get Merchant From Table
       MerchantOwner merchantOwner = merchantOwnerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Id Not Found","id",id));
       // response merchant owner
       return merchantOwnerMapper.toResponse(merchantOwner);
    }

    @Override
    public void deactivateMerchantOwner(Long id){
        MerchantOwner merchant = merchantOwnerRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("ID NOT FOUND","id",id));
        merchant.setStatus(MerchantStatus.INACTIVE);
        merchantOwnerRepository.save(merchant);
    }


    public static class CustomUserService {
    }
}

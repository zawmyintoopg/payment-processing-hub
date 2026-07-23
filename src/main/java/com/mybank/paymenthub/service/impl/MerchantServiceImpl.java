package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.entity.*;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantMapper;
import com.mybank.paymenthub.repository.*;
import com.mybank.paymenthub.service.MerchantService;
import com.mybank.paymenthub.service.NumberSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {


    private final MerchantRepository merchantRepository;
    private final MerchantOwnerRepository merchantOwnerRepository;
    private final MerchantCategoryRepository merchantCategoryRepository;
    private final MerchantSegmentRepository merchantSegmentRepository;
    private final MerchantMapper merchantMapper;
    private final NumberSequenceService numberSequenceService;

    @Transactional
    @Override
    public MerchantResponseDTO createMerchant(MerchantRequestDTO requestDTO){

        // check duplicate registration no
        if(merchantRepository.existsByMerchantRegistrationNo(requestDTO.getMerchantRegistrationNo())){
            throw new DuplicateException("Merchant Registration Number is Duplicate");
        }
        //check duplicate merchant number
        //find relationship for merchant owner
        MerchantOwner merchantOwner = merchantOwnerRepository.findById(requestDTO.getMerchantOwnerId()).orElseThrow(
                ()->new ResourceNotFoundException("Merchant Owner","merchant_owner_id",requestDTO.getMerchantOwnerId())
        );

        //find relationship for merchant category
        MerchantCategory merchantCategory = merchantCategoryRepository.findById(requestDTO.getMerchantCategoryId()).orElseThrow(
                ()->new ResourceNotFoundException("Merchant Category","merchant_category_id",requestDTO.getMerchantCategoryId())
        );

        // find relationship for merchant category
        MerchantSegment merchantSegment = merchantSegmentRepository.findById(requestDTO.getMerchantSegmentId()).orElseThrow(
                ()-> new ResourceNotFoundException("Merchant Segment","merchant_segment_id",requestDTO.getMerchantSegmentId())
        );

        // create object to save data
        Merchant merchant = new Merchant();

        merchant.setMerchantName(requestDTO.getMerchantName());
        merchant.setMerchantRegistrationNo(requestDTO.getMerchantRegistrationNo());
        merchant.setBusinessRegistrationDate(requestDTO.getBusinessRegistrationDate());

        merchant.setMerchantSegment(merchantSegment);
        merchant.setMerchantCategory(merchantCategory);
        merchant.setMerchantOwner(merchantOwner);
        merchant.setStatus(requestDTO.getStatus());
        String merchantNumber =
                numberSequenceService.generateMerchantNumber();

        System.out.println("Generated Merchant Number = " + merchantNumber);

        merchant.setMerchantNumber(merchantNumber);
       // merchant.setMerchantNumber("10000002");

        //save merchants
        Merchant savedMerchant = merchantRepository.save(merchant);

        return merchantMapper.toResponse(savedMerchant);

    }
    //=========================================GET ALL MERCHANTS
    // ====================================
    @Override
    public List<MerchantResponseDTO> getAllMerchantList() {
        //Get From Data to assign object
        List<Merchant> merchants = merchantRepository.findAll();
       // Change to Stream and return list
        return merchants.stream()
                .map(merchantMapper::toResponse)
                .toList();
    }
    //=========================================GET ONE MERCHANTS====================================
    @Override
    public MerchantResponseDTO getMerchantById(Long id){
        Merchant merchant = merchantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(
                "Merchant",
                "id",
                id
        ));

        return  merchantMapper.toResponse(merchant);
    }
    //=========================================UPDATE MERCHANT====================================
    @Override
    public MerchantResponseDTO updateMerchant(Long id , MerchantRequestDTO requestDTO){
        //get data by id
        Merchant merchant = merchantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Merchant ID",
                "id",
                id)
        );

        //duplicate check registration no
        if(merchantRepository.existsByMerchantRegistrationNoAndIdNot(requestDTO.getMerchantRegistrationNo(),id)){
            throw new DuplicateException(
                    "Merchant Registration Number is already Exists"
            );
        }
        //get date merchant category
        MerchantCategory merchantCategory = merchantCategoryRepository.findById(requestDTO.getMerchantCategoryId()).orElseThrow(()->
                new ResourceNotFoundException("Merchant Category","id",requestDTO.getMerchantCategoryId()));

        //get date merchant segment
        MerchantSegment merchantSegment = merchantSegmentRepository.findById(requestDTO.getMerchantSegmentId()).orElseThrow(()->
                new ResourceNotFoundException("Merchant Segment","id",requestDTO.getMerchantSegmentId()));

        //get date merchant owner
        MerchantOwner merchantOwner  = merchantOwnerRepository.findById(requestDTO.getMerchantOwnerId()).orElseThrow(()->
                new ResourceNotFoundException("Merchant Owner","id",requestDTO.getMerchantOwnerId()));

        merchant.setMerchantName(requestDTO.getMerchantName());
        merchant.setMerchantRegistrationNo(requestDTO.getMerchantRegistrationNo());
        merchant.setBusinessRegistrationDate(requestDTO.getBusinessRegistrationDate());
        merchant.setMerchantCategory(merchantCategory);
        merchant.setMerchantSegment(merchantSegment);
        merchant.setMerchantOwner(merchantOwner);
        merchant.setStatus(requestDTO.getStatus());

        Merchant updatedMerchant = merchantRepository.save(merchant);

        return merchantMapper.toResponse(updatedMerchant);

    }

    ////=========================================DEACTIVATE MERCHANTS====================================
    @Override
    // DE ACTIVEATE
    public void deactivateMerchant(Long id){
        Merchant merchant = merchantRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Merchant ID",
                "id",
                id)
        );
        if(merchant.getStatus() == MerchantStatus.INACTIVE){
            throw new IllegalStateException(
                    "Merchant is already inactive"
            );
        }
        merchant.setStatus(MerchantStatus.INACTIVE);
        merchantRepository.save(merchant);
    }

    //=========================================SEARCH MERCHANTS====================================
    @Override
    public List<MerchantResponseDTO> searchMerchantByName(String name){
         List<Merchant> merchantList = merchantRepository.findByMerchantNameContainingIgnoreCase(name);

         return merchantList.stream().map(merchantMapper::toResponse).toList();
    }
    @Override
    public Page<MerchantResponseDTO> getMerchantPagination(int page, int size){

        if(size > 100){
            size = 100;
        }

        Pageable pageable  = PageRequest.of(
                page,
                size,
                Sort.by("createdDate").descending()
        );

        Page<Merchant> merchantPage = merchantRepository.findAll(pageable);

        return merchantPage.map(
                merchantMapper::toResponse
        );
    }

}

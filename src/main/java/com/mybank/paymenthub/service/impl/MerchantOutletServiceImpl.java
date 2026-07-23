package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantOutletMapper;
import com.mybank.paymenthub.repository.MerchantOutletRepository;
import com.mybank.paymenthub.repository.MerchantRepository;
import com.mybank.paymenthub.service.MerchantOutletService;
import com.mybank.paymenthub.service.NumberSequenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MerchantOutletServiceImpl implements MerchantOutletService {

    private final MerchantOutletRepository merchantOutletRepository;
    private final NumberSequenceService numberSequenceService;
    private final MerchantRepository merchantRepository;
    private final MerchantOutletMapper merchantOutletMapper;
    //create merchant outlet
    public MerchantOutletResponseDTO createMerchantOutlet(MerchantOutletRequestDTO requestDTO){


        MerchantOutlet merchantOutlet = new MerchantOutlet();
        merchantOutlet.setOutletNumber("30000002");
        merchantOutlet.setOutletName(requestDTO.getOutletName());
        merchantOutlet.setAddress(requestDTO.getAddress());
        merchantOutlet.setCity(requestDTO.getCity());
        merchantOutlet.setPhone(requestDTO.getPhone());
        Merchant merchant = merchantRepository.findById(requestDTO.getMerchantId()).orElseThrow(()->
                new ResourceNotFoundException("Merchant","merchantId",requestDTO.getMerchantId())
        );
        merchantOutlet.setMerchant(merchant);
        merchantOutlet.setStatus(MerchantStatus.ACTIVE);

        MerchantOutlet savedMerchant =   merchantOutletRepository.save(merchantOutlet);

        return merchantOutletMapper.toResponse(savedMerchant);
    }
    // all merchant outlet list
    public List<MerchantOutletResponseDTO> getAllMerchantOutletList(){

       List<MerchantOutlet> merchantOutlet = merchantOutletRepository.findAll();
       return merchantOutlet.
               stream().
               map(merchantOutletMapper::toResponse).
               toList();
    }
    // a merchant list
    //MerchantOutletResponseDTO getMerchantById(Long id);
    // update merchant outlet
   // MerchantOutletRequestDTO updateMerchantOutlet(MerchantOutletRequestDTO requestDTO, Long id);
    // delete merchant outlet
   // void deleteMerchantOutlet(Long id);
    //search by merchant outlet name
   // MerchantOutletResponseDTO searchByOutletNumber(String outletNumber);
    //search by merchant id
    //MerchantOutletResponseDTO searchByMerchantId(String merchantId);
    //search by merchant status
   // MerchantOutletResponseDTO searchByMerchantStatus(String MerchantStatus);

    public static class CustomUserDetailsService {
    }
}

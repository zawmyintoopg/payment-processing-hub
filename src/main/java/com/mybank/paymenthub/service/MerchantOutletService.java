package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;

import java.util.List;

public interface MerchantOutletService {

    //create merchant outlet
    MerchantOutletResponseDTO createMerchantOutlet(MerchantOutletRequestDTO requestDTO);
    // all merchant list
    List<MerchantOutletResponseDTO> getAllMerchantOutletList();
    // a merchant list
   // MerchantOutletResponseDTO getMerchantById(Long id);
    // update merchant outlet
   // MerchantOutletRequestDTO updateMerchantOutlet(MerchantOutletRequestDTO requestDTO, Long id);
    // delete merchant outlet
   // void deleteMerchantOutlet(Long id);
    //search by merchant outlet name
   // MerchantOutletResponseDTO searchByOutletNumber(String outletNumber);
    //search by merchant id
   // MerchantOutletResponseDTO searchByMerchantId(String merchantId);
    //search by merchant status
    //MerchantOutletResponseDTO searchByMerchantStatus(String MerchantStatus);


}

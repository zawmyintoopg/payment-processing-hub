package com.mybank.paymenthub.service;


import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface MerchantService {


    List<MerchantResponseDTO> getAllMerchantList();


    MerchantResponseDTO getMerchantById(Long id);


    MerchantResponseDTO createMerchant(
            MerchantRequestDTO requestDTO
    );


    MerchantResponseDTO updateMerchant(
            Long id,
            MerchantRequestDTO requestDTO
    );


    void deactivateMerchant(Long id);


    List<MerchantResponseDTO> searchMerchantByName(
            String name
    );


    Page<MerchantResponseDTO> getMerchantPagination(
            int page,
            int size
    );

}
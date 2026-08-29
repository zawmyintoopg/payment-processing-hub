package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletMerchantHistoryResponseDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public interface MerchantOutletService {

    MerchantOutletResponseDTO createMerchantOutlet(
            MerchantOutletRequestDTO requestDTO);
    MerchantOutletResponseDTO updateMerchantOutlet(
            Long id,
            MerchantOutletRequestDTO requestDTO
    );
    Page<MerchantOutletResponseDTO> getAllMerchantOutlets(
            String search, //user search keyword  search ="Yangon"
            Pageable pageable //pagination information
    );
    MerchantOutletResponseDTO getMerchantOutletById(
            Long id
    );
    Page<MerchantOutletMerchantHistoryResponseDTO>
    getMerchantHistory(
            Long merchantOutletId,
            Pageable pageable
    );
    void activateMerchantOutlet(Long id);

    void deActivateMerchantOutlet(Long id);
}

package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Service interface for Merchant business operations.
 *
 * Defines business methods for creating, updating,
 * retrieving and managing merchant status.
 */
public interface MerchantService {

    MerchantResponseDTO createMerchant(
            MerchantRequestDTO requestDTO
    );
    MerchantResponseDTO updateMerchant(
            Long id,
            MerchantRequestDTO requestDTO
    );
    Page<MerchantResponseDTO> getAllMerchants(
            String search,
            Pageable pageable
    );
    MerchantResponseDTO getMerchantById(
            Long id
    );
    void deactivateMerchant(
            Long id
    );
    void activateMerchant(
            Long id
    );
}

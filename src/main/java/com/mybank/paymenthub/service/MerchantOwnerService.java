package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantOwnerRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOwnerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Service interface for Merchant Owner business operations.
 *
 * Defines business methods for creating, updating,
 * retrieving and managing merchant owner status.
 */
public interface MerchantOwnerService {

    MerchantOwnerResponseDTO createMerchantOwner(
            MerchantOwnerRequestDTO requestDTO
    );
    MerchantOwnerResponseDTO updateMerchantOwner(
            Long id,
            MerchantOwnerRequestDTO requestDTO
    );
    Page<MerchantOwnerResponseDTO> getAllMerchantOwners(
            String search,
            Pageable pageable
    );
    MerchantOwnerResponseDTO getMerchantOwnerById(
            Long id
    );
    void deactivateMerchantOwner(
            Long id
    );
    void activateMerchantOwner(
            Long id
    );
}

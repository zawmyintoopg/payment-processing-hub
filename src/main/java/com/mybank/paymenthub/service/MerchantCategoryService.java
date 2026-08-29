package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantCategoryRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantCategoryResponseDTO;
import java.util.List;

public interface MerchantCategoryService {
    List<MerchantCategoryResponseDTO> getAllMerchantCategories();
    MerchantCategoryResponseDTO getMerchantCategoryById(Long id);
    MerchantCategoryResponseDTO createMerchantCategory(
            MerchantCategoryRequestDTO requestDTO
    );
    MerchantCategoryResponseDTO updateMerchantCategory(
            Long id,
            MerchantCategoryRequestDTO requestDTO
    );
    void deactivateMerchantCategory(
            Long id
    );
    void activateMerchantCategory(
            Long id
    );
}

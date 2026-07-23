package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantCategoryRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantCategoryResponseDTO;
import com.mybank.paymenthub.entity.MerchantCategory;

import java.util.List;

public interface MerchantCategoryService {
    public List<MerchantCategoryResponseDTO> getAllMerchantCategories();
    public MerchantCategoryResponseDTO getMerchantCategoryById(Long id);
    public MerchantCategoryResponseDTO createMerchantCategory(MerchantCategoryRequestDTO requestDTO);
    public MerchantCategoryResponseDTO updateMerchantCategory(Long id,MerchantCategoryRequestDTO requestDTO);
    void delete(Long id);
}

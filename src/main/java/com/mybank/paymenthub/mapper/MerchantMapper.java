package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.entity.Merchant;
import org.springframework.stereotype.Component;

@Component
public class MerchantMapper {

    public MerchantResponseDTO toResponse(Merchant merchant){
        MerchantResponseDTO merchantResponseDTO = new MerchantResponseDTO();

        merchantResponseDTO.setId(merchant.getId());
        merchantResponseDTO.setMerchantNumber(merchant.getMerchantNumber());
        merchantResponseDTO.setMerchantName(merchant.getMerchantName());
        merchantResponseDTO.setMerchantRegistrationNo(merchant.getMerchantRegistrationNo());
        merchantResponseDTO.setBusinessRegistrationDate(merchant.getBusinessRegistrationDate());

        if (merchant.getMerchantOwner() != null) {
            merchantResponseDTO.setMerchantOwnerId(merchant.getMerchantOwner().getId());
        }

        if (merchant.getMerchantSegment() != null) {
            merchantResponseDTO.setMerchantSegmentId(merchant.getMerchantSegment().getId());
        }

        if (merchant.getMerchantCategory() != null) {
            merchantResponseDTO.setMerchantCategoryId(merchant.getMerchantCategory().getId());
        }

        merchantResponseDTO.setStatus(merchant.getStatus());
        merchantResponseDTO.setCreatedAt(merchant.getCreatedDate());

        return merchantResponseDTO;

    }
}

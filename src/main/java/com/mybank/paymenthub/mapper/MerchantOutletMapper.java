package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import com.mybank.paymenthub.entity.MerchantOutlet;
import org.springframework.stereotype.Component;

@Component
public class MerchantOutletMapper {
    public MerchantOutletResponseDTO toResponse(MerchantOutlet merchantOutlet){
        MerchantOutletResponseDTO merchantOutletResponseDTO = new MerchantOutletResponseDTO();

        merchantOutletResponseDTO.setId(merchantOutlet.getId());
        merchantOutletResponseDTO.setOutletNumber(merchantOutlet.getOutletNumber());
        merchantOutletResponseDTO.setOutletName(merchantOutlet.getOutletName());
        merchantOutletResponseDTO.setAddress(merchantOutlet.getAddress());
        merchantOutletResponseDTO.setCity(merchantOutlet.getCity());
        merchantOutletResponseDTO.setPhone(merchantOutlet.getPhone());
        if(merchantOutlet.getMerchant() != null){
            merchantOutletResponseDTO.setMerchantId(merchantOutlet.getMerchant().getId());
        }
        merchantOutletResponseDTO.setStatus(merchantOutlet.getStatus());
        merchantOutletResponseDTO.setCreatedAt(merchantOutlet.getCreatedDate());

        return merchantOutletResponseDTO;

    }
}

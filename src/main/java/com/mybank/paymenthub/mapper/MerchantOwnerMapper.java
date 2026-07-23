package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.response.MerchantOwnerResponseDTO;
import com.mybank.paymenthub.entity.MerchantOwner;
import org.springframework.stereotype.Component;

@Component
public class MerchantOwnerMapper {
    public MerchantOwnerResponseDTO toResponse(MerchantOwner merchantOwner){

        MerchantOwnerResponseDTO merchantOwnerResponseDTO = new MerchantOwnerResponseDTO();

        merchantOwnerResponseDTO.setId(merchantOwner.getId());
        merchantOwnerResponseDTO.setOwnerNumber(merchantOwner.getOwnerNumber());
        merchantOwnerResponseDTO.setOwnerName(merchantOwner.getOwnerName());
        merchantOwnerResponseDTO.setRegistrationNo(merchantOwner.getRegistrationNo());
        merchantOwnerResponseDTO.setOwnerType(merchantOwner.getOwnerType());
        merchantOwnerResponseDTO.setEmail(merchantOwner.getEmail());
        merchantOwnerResponseDTO.setPhone(merchantOwner.getPhone());
        merchantOwnerResponseDTO.setStatus(merchantOwner.getStatus());

        merchantOwnerResponseDTO.setCreatedDate(merchantOwner.getCreatedDate());
        merchantOwnerResponseDTO.setUpdatedDate(merchantOwner.getUpdatedDate());



        return merchantOwnerResponseDTO;

    }
}

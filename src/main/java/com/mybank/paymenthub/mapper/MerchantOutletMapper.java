package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.enums.OutletStatus;
import org.springframework.stereotype.Component;

@Component
public class MerchantOutletMapper {


    public MerchantOutlet toEntity(MerchantOutletRequestDTO requestDTO){
        MerchantOutlet entity = new MerchantOutlet();

        entity.setOutletName(requestDTO.getOutletName());
        entity.setAddress(requestDTO.getAddress());
        entity.setCity(requestDTO.getCity());
        entity.setPhone(requestDTO.getPhone());
        entity.setStatus(OutletStatus.ACTIVE);

        return entity;
    }

    public MerchantOutletResponseDTO toResponse(MerchantOutlet entity){

        MerchantOutletResponseDTO responseDTO =
                new MerchantOutletResponseDTO();

        responseDTO.setId(entity.getId());
        responseDTO.setOutletNumber(entity.getOutletNumber());
        responseDTO.setOutletName(entity.getOutletName());
        if (entity.getMerchant() != null ){
            responseDTO.setMerchantId(entity.getMerchant().getId());
            responseDTO.setMerchantName(entity.getMerchant().getMerchantName());
        }
        responseDTO.setAddress(entity.getAddress());
        responseDTO.setCity(entity.getCity());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setStatus(entity.getStatus());
        responseDTO.setCreatedDate(
                entity.getCreatedDate()
        );
        responseDTO.setUpdatedDate(
                entity.getUpdatedDate()
        );

        return responseDTO;
    }

    public void updateEntity(
            MerchantOutletRequestDTO requestDTO
            ,MerchantOutlet entity
    ){

        if(requestDTO.getOutletName() != null){
           entity.setOutletName(requestDTO.getOutletName());
        }

        if(requestDTO.getAddress() != null){
            entity.setAddress(requestDTO.getAddress());
        }

        if(requestDTO.getCity() != null){
            entity.setCity(requestDTO.getCity());
        }

        if(requestDTO.getPhone() != null){
            entity.setPhone(requestDTO.getPhone());
        }
    }
}

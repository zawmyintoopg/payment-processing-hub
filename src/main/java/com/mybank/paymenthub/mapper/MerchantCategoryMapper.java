package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.MerchantCategoryRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantCategoryResponseDTO;
import com.mybank.paymenthub.entity.MerchantCategory;
import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import org.springframework.stereotype.Component;

@Component
public class MerchantCategoryMapper {

    public MerchantCategory toEntity(MerchantCategoryRequestDTO requestDTO){

        MerchantCategory entity = new MerchantCategory();

        entity.setCategoryCode(requestDTO.getCategoryCode());
        entity.setCategoryName(requestDTO.getCategoryName());
        entity.setDescription(requestDTO.getDescription());

        entity.setMerchantCategoryStatus(MerchantCategoryStatus.ACTIVE);

        return entity;
    }

    public MerchantCategoryResponseDTO toResponse(
            MerchantCategory entity){

        MerchantCategoryResponseDTO response =
                new MerchantCategoryResponseDTO();
        response.setId(entity.getId());
        response.setCategoryCode(entity.getCategoryCode());
        response.setCategoryName(entity.getCategoryName());
        response.setDescription(entity.getDescription());

        response.setStatus(entity.getMerchantCategoryStatus());

        return response;
    }

    public void updateEntity(
            MerchantCategoryRequestDTO requestDTO,
            MerchantCategory entity
    ){


        if(requestDTO.getCategoryCode() != null){
            entity.setCategoryCode(requestDTO.getCategoryCode());
        }
        if(requestDTO.getCategoryName() != null){
            entity.setCategoryName(requestDTO.getCategoryName());
        }
        if(requestDTO.getDescription() != null){
            entity.setDescription(requestDTO.getDescription());
        }
    }
}

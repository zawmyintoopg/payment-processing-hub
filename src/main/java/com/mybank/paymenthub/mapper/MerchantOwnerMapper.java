package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.MerchantOwnerRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOwnerResponseDTO;
import com.mybank.paymenthub.entity.MerchantOwner;

import org.springframework.stereotype.Component;

/**
 * Mapper for converting between MerchantOwner entities and DTOs
 */
@Component
public class MerchantOwnerMapper {
    /**
     * Convert MerchantOwnerRequestDTO to MerchantOwner entity
     * @param requestDTO request data
     * @return entity
     */
    public MerchantOwner toEntity(MerchantOwnerRequestDTO requestDTO){

        MerchantOwner entity = new MerchantOwner();

        entity.setOwnerName(requestDTO.getOwnerName());
        entity.setOwnerType(requestDTO.getOwnerType());
        entity.setRegistrationNo(requestDTO.getRegistrationNo());
        entity.setEmail(requestDTO.getEmail());
        entity.setPhone(requestDTO.getPhone());

        return entity;
    }

    /**
     * Converts a MerchantOwner entity to MerchantOwnerResponseDTO
     * @param entity MerchantOwner entity
     * @return responseDTO
     */
    public MerchantOwnerResponseDTO toResponse(
            MerchantOwner entity
        ){

        MerchantOwnerResponseDTO responseDTO = new MerchantOwnerResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setOwnerNumber(entity.getOwnerNumber());
        responseDTO.setOwnerName(entity.getOwnerName());
        responseDTO.setOwnerType(entity.getOwnerType());
        responseDTO.setRegistrationNo(entity.getRegistrationNo());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setPhone(entity.getPhone());
        responseDTO.setCreatedDate(entity.getCreatedDate());
        responseDTO.setUpdatedDate(entity.getUpdatedDate());
        responseDTO.setStatus(entity.getStatus());

        return responseDTO;
    }

    /**
     * Updates an existing MerchantOwner entity using requestDTO
     * System-managed field such as ownerNumber is not modified.
     *
     * @param requestDTO request data
     * @param entity existing MerchantOwner entity
     */
    public void updateEntity(
            MerchantOwnerRequestDTO requestDTO,
            MerchantOwner entity
    ){
        if (requestDTO.getOwnerName() != null){
            entity.setOwnerName(requestDTO.getOwnerName());
        }
        if (requestDTO.getRegistrationNo() != null){
            entity.setRegistrationNo(requestDTO.getRegistrationNo());
        }
        if (requestDTO.getOwnerType() != null){
            entity.setOwnerType(requestDTO.getOwnerType());
        }
        if (requestDTO.getEmail() != null){
            entity.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getPhone() != null){
            entity.setPhone(requestDTO.getPhone());
        }
    }
}

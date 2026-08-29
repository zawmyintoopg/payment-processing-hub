package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantCategory;
import com.mybank.paymenthub.entity.MerchantOwner;
import com.mybank.paymenthub.entity.MerchantSegment;
import com.mybank.paymenthub.enums.MerchantStatus;
import org.springframework.stereotype.Component;

@Component
public class MerchantMapper {
    public Merchant toEntity(MerchantRequestDTO requestDTO){

        Merchant entity = new Merchant();

        entity.setMerchantName(requestDTO.getMerchantName());
        entity.setBusinessRegistrationDate(requestDTO.getBusinessRegistrationDate());
        entity.setStatus(MerchantStatus.ACTIVE);
        return entity;
    }
    /**
     * Converts a Merchant entity to MerchantResponseDTO
     * @param entity Merchant entity
     * @return MerchantResponseDTO
     */
    public MerchantResponseDTO toResponse(
            Merchant entity
    ){

        MerchantResponseDTO responseDTO = new MerchantResponseDTO();

        responseDTO.setId(
                entity.getId()
        );
        responseDTO.setMerchantNumber(
                entity.getMerchantNumber()
        );
        responseDTO.setMerchantName(
                entity.getMerchantName()
        );
        responseDTO.setBusinessRegistrationDate(
                entity.getBusinessRegistrationDate()
        );
        MerchantOwner owner = entity.getMerchantOwner();
        if(owner != null){
            responseDTO.setMerchantOwnerId(owner.getId());
            responseDTO.setMerchantOwnerName(owner.getOwnerName());
        }

        MerchantSegment segment = entity.getMerchantSegment();
        if( segment != null) {
            responseDTO.setMerchantSegmentId(segment.getId());
            responseDTO.setMerchantSegmentName(segment.getMerchantSegmentName());
        }

        MerchantCategory category = entity.getMerchantCategory();
        if (category != null){
            responseDTO.setMerchantCategoryId(category.getId());
            responseDTO.setMerchantCategoryName(category.getCategoryName());
        }
        responseDTO.setCreatedDate(
                entity.getCreatedDate()
        );
        responseDTO.setUpdatedDate(
                entity.getUpdatedDate()
        );
        responseDTO.setStatus(
                entity.getStatus()
        );

        return responseDTO;
    }

    /**
     * Updates an existing Merchant entity using requestDTO
     * System-managed field such as merchantNumber is not modified.
     *
     * @param requestDTO request data
     * @param entity existing Merchant entity
     */
    public void updateEntity(
            MerchantRequestDTO requestDTO,
            Merchant entity
    ){
        if (requestDTO.getMerchantName() != null){
            entity.setMerchantName(
                    requestDTO.getMerchantName()
            );
        }
        if (requestDTO.getBusinessRegistrationDate() != null){
            entity.setBusinessRegistrationDate(
                    requestDTO.getBusinessRegistrationDate()
            );
        }
    }
}

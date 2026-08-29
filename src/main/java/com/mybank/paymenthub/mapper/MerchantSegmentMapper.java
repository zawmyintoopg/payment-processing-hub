package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.MerchantSegmentRequest;
import com.mybank.paymenthub.dto.response.MerchantSegmentResponse;
import com.mybank.paymenthub.entity.MerchantSegment;
import com.mybank.paymenthub.enums.Status;
import org.springframework.stereotype.Component;

@Component
public class MerchantSegmentMapper {

    public MerchantSegment toEntity(MerchantSegmentRequest request){

        MerchantSegment entity = new MerchantSegment();

        entity.setMerchantSegmentCode(
                request.getMerchantSegmentCode()
        );
        entity.setMerchantSegmentName(
                request.getMerchantSegmentName()
        );
        entity.setStatus(
                Status.ACTIVE
        );
        return entity;
    }

    public MerchantSegmentResponse toResponse(MerchantSegment entity){

        MerchantSegmentResponse response = new MerchantSegmentResponse();

        response.setMerchantSegmentCode(
                entity.getMerchantSegmentCode()
        );
        response.setMerchantSegmentName(
                entity.getMerchantSegmentName()
        );
        response.setMerchantSegmentStatus(
                entity.getStatus()
        );

        return response;
    }

    public void updateEntity(
            MerchantSegmentRequest merchantSegmentRequest,
            MerchantSegment merchantSegment
    ){
        if(merchantSegment.getMerchantSegmentCode() != null){
            merchantSegment.setMerchantSegmentCode(
                    merchantSegment.getMerchantSegmentCode()
            );
        }

        if(merchantSegment.getMerchantSegmentName() != null){
            merchantSegment.setMerchantSegmentCode(
                    merchantSegment.getMerchantSegmentName()
            );
        }
    }
}

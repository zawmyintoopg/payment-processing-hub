package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.MerchantContractCreateRequest;
import com.mybank.paymenthub.dto.response.MerchantContractResponse;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantContract;
import com.mybank.paymenthub.enums.ContractStatus;
import org.springframework.stereotype.Component;

@Component
public class MerchantContractMapper {

    public MerchantContract toEntity(
            MerchantContractCreateRequest request
    ){

        MerchantContract entity = new MerchantContract();

        entity.setContractDate(
                request.getContractDate()
        );
        entity.setContractStartDate(
                request.getContractStartDate()
        );
        entity.setContractEndDate(
                request.getContractEndDate()
        );
        entity.setCommissionRate(
                request.getCommissionRate()
        );
        entity.setSettlementCycle(
                request.getSettlementCycle()
        );
        entity.setContractType(
                request.getContractType()
        );
        entity.setStatus(
                ContractStatus.ACTIVE
        );
        return entity;
    }

    public MerchantContractResponse toResponse(
            MerchantContract entity
    ){
        MerchantContractResponse response =
                new MerchantContractResponse();

        response.setId(entity.getId());
        response.setContractNumber(entity.getContractNumber());
        Merchant merchant = entity.getMerchant();
        if( merchant != null) {
            response.setMerchantId(merchant.getId());
            response.setMerchantName(merchant.getMerchantName());
        }

        response.setContractType(
                entity.getContractType()
        );

        response.setContractDate(
                entity.getContractDate()
        );

        response.setContractStartDate(
                entity.getContractStartDate()
        );


        response.setContractEndDate(
                entity.getContractEndDate()
        );

        response.setCommissionRate(
                entity.getCommissionRate()
        );

        response.setSettlementCycle(
                entity.getSettlementCycle()
        );

        response.setStatus(
                entity.getStatus()
        );
        return response;
    }

    public void updateEntity(
            MerchantContract entity,
            MerchantContractCreateRequest request
    ){
        if( request.getContractType() != null) {
            entity.setContractType(
                    request.getContractType()
            );
        }
        if( request.getContractStartDate() != null) {
            entity.setContractStartDate(
                    request.getContractStartDate()
            );
        }
        if( request.getContractEndDate() != null) {
            entity.setContractEndDate(
                    request.getContractEndDate()
            );
        }
        if(  request.getCommissionRate() != null) {
            entity.setCommissionRate(
                    request.getCommissionRate()
            );
        }
        if(  request.getSettlementCycle() != null) {
            entity.setSettlementCycle(
                    request.getSettlementCycle()
            );
        }
    }
}


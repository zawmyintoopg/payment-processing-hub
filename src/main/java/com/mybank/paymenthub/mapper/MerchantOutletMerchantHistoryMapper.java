package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.response.MerchantOutletMerchantHistoryResponseDTO;
import com.mybank.paymenthub.entity.MerchantOutletMerchantHistory;
import org.springframework.stereotype.Component;

@Component
public class MerchantOutletMerchantHistoryMapper {

    public MerchantOutletMerchantHistoryResponseDTO toResponse(
            MerchantOutletMerchantHistory entity
    ) {

        MerchantOutletMerchantHistoryResponseDTO response =
                new MerchantOutletMerchantHistoryResponseDTO();

        response.setId(entity.getId());

        if (entity.getMerchantOutlet() != null) {

            response.setMerchantOutletId(
                    entity.getMerchantOutlet().getId()
            );

            response.setOutletNumber(
                    entity.getMerchantOutlet().getOutletNumber()
            );

            response.setOutletName(
                    entity.getMerchantOutlet().getOutletName()
            );
        }

        if (entity.getMerchant() != null) {

            response.setMerchantId(
                    entity.getMerchant().getId()
            );

            response.setMerchantName(
                    entity.getMerchant().getMerchantName()
            );
        }

        response.setEffectiveFrom(
                entity.getEffectiveFrom()
        );

        response.setEffectiveTo(
                entity.getEffectiveTo()
        );

        response.setChangeReason(
                entity.getChangeReason()
        );

        response.setCreatedDate(
                entity.getCreatedDate()
        );

        response.setUpdatedDate(
                entity.getUpdatedDate()
        );

        return response;
    }

}
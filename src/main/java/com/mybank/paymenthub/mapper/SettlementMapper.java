package com.mybank.paymenthub.mapper;


import com.mybank.paymenthub.dto.response.SettlementResponse;
import com.mybank.paymenthub.entity.Settlement;
import org.springframework.stereotype.Component;

@Component
public class SettlementMapper {

     public SettlementResponse toResponse(Settlement entity){

         SettlementResponse response =
                 new SettlementResponse();

         response.setSettlementNumber(
                 entity.getSettlementNumber()
         );
         response.setMerchantId(
                 entity.getMerchant().getId()
         );
         response.setMerchantBankAccountId(
                 entity.getMerchantBankAccount().getId()
         );
         response.setSettlementDate(
                 entity.getSettlementDate()
         );
         response.setTotalAmount(
                 entity.getTotalAmount()
         );
         response.setTransactionCount(
                 entity.getTransactionCount()
         );
         response.setStatus(
                 entity.getStatus()
         );

         return response;

     }
}

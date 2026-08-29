package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.entity.MerchantBankAccount;

public class MerchantBankAccountMapper {

    public MerchantBankAccount toEntity(MerchantBankAccount request){

        MerchantBankAccount entity = new MerchantBankAccount();

        entity.setAccountName(request.getAccountName());
        entity.setOpenedDate(request.getOpenedDate());

        return entity;
    }
}

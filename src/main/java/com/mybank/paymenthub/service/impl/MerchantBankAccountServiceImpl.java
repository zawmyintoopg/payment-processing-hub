package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantBankAccountRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantBankAccountResponse;
import com.mybank.paymenthub.entity.*;
import com.mybank.paymenthub.enums.AccountStatus;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.repository.*;
import com.mybank.paymenthub.service.MerchantBankAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantBankAccountServiceImpl
        implements MerchantBankAccountService {

    private final MerchantBankAccountRepository merchantBankAccountRepository;
    private final MerchantRepository merchantRepository;
    private final BankRepository bankRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CurrencyRepository currencyRepository;


    @Override
    @Transactional
    public MerchantBankAccountResponse create(
            MerchantBankAccountRequestDTO request
    ) {

        // =====================================================
        // STEP 1: Find Merchant
        // =====================================================

        Merchant merchant =
                merchantRepository.findById(
                        request.getMerchantId()
                ).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Merchant Not Found !",
                                "merchantId",
                                request.getMerchantId()
                        )
                );


        // =====================================================
        // STEP 2: Find Bank
        // =====================================================

        Bank bank =
                bankRepository.findById(
                        request.getBankId()
                ).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Bank Not Found !",
                                "bankId",
                                request.getBankId()
                        )
                );


        // =====================================================
        // STEP 3: Find Account Type
        // =====================================================

        AccountType accountType =
                accountTypeRepository.findById(
                        request.getAccountTypeId()
                ).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Account Type Not Found !",
                                "accountTypeId",
                                request.getAccountTypeId()
                        )
                );


        // =====================================================
        // STEP 4: Find Currency
        // =====================================================

        Currency currency =
                currencyRepository.findById(
                        request.getCurrencyId()
                ).orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Currency Not Found !",
                                "currencyId",
                                request.getCurrencyId()
                        )
                );


        // =====================================================
        // STEP 5: Create Entity
        // =====================================================

        MerchantBankAccount merchantBankAccount =
                new MerchantBankAccount();

        merchantBankAccount.setMerchant(
                merchant
        );

        merchantBankAccount.setBank(
                bank
        );

        merchantBankAccount.setAccountNumber(
                request.getAccountNumber()
        );

        merchantBankAccount.setAccountName(
                request.getAccountName()
        );

        merchantBankAccount.setAccountType(
                accountType
        );

        merchantBankAccount.setOpenedDate(
                request.getAccountOpenedDate()
        );

        merchantBankAccount.setCurrency(
                currency
        );

        merchantBankAccount.setStatus(
                AccountStatus.ACTIVE
        );


        // =====================================================
        // STEP 6: Save
        // =====================================================

        MerchantBankAccount savedAccount =
                merchantBankAccountRepository.save(
                        merchantBankAccount
                );


        // =====================================================
        // STEP 7: Response
        // =====================================================

        MerchantBankAccountResponse response =
                new MerchantBankAccountResponse();

        response.setId(
                savedAccount.getId()
        );

        response.setMerchantId(
                savedAccount.getMerchant().getId()
        );

        response.setBankId(
                savedAccount.getBank().getId()
        );

        response.setAccountNumber(
                savedAccount.getAccountNumber()
        );

        response.setAccountName(
                savedAccount.getAccountName()
        );

        response.setAccountTypeId(
                savedAccount.getAccountType().getId()
        );

        response.setOpenedDate(
                savedAccount.getOpenedDate()
        );

        response.setCurrencyId(
                savedAccount.getCurrency().getId()
        );

        response.setStatus(
                savedAccount.getStatus()
        );

        return response;
    }
}
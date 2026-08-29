package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.AccountTypeRequestDTO;
import com.mybank.paymenthub.dto.response.AccountTypeResponse;
import com.mybank.paymenthub.entity.AccountType;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.repository.AccountTypeRepository;
import com.mybank.paymenthub.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountTypeServiceImpl
        implements AccountTypeService {

    private final AccountTypeRepository accountTypeRepository;

    @Override
    public AccountTypeResponse create(
            AccountTypeRequestDTO request
    ) {

        // Check duplicate
        if (accountTypeRepository.existsByAccountType(
                request.getAccountType()
        )) {
            throw new BusinessException(
                    "Account Type already exists"
            );
        }

        AccountType accountType =
                new AccountType();

        accountType.setAccountType(
                request.getAccountType()
        );

        accountType.setDescription(
                request.getDescription()
        );

        accountType.setStatus(
                request.getStatus()
        );

        AccountType savedAccountType =
                accountTypeRepository.save(accountType);

        return mapToResponse(savedAccountType);
    }

    @Override
    public AccountTypeResponse getById(Long id) {

        AccountType accountType =
                accountTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Account Type Not Found",
                                        "accountTypeId",
                                        id
                                )
                        );

        return mapToResponse(accountType);
    }

    @Override
    public List<AccountTypeResponse> getAll() {

        return accountTypeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public AccountTypeResponse update(
            Long id,
            AccountTypeRequestDTO request
    ) {

        AccountType accountType =
                accountTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Account Type Not Found",
                                        "accountTypeId",
                                        id
                                )
                        );

        accountType.setAccountType(
                request.getAccountType()
        );

        accountType.setDescription(
                request.getDescription()
        );

        accountType.setStatus(
                request.getStatus()
        );

        AccountType updatedAccountType =
                accountTypeRepository.save(accountType);

        return mapToResponse(updatedAccountType);
    }

    @Override
    public void delete(Long id) {

        AccountType accountType =
                accountTypeRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Account Type Not Found",
                                        "accountTypeId",
                                        id
                                )
                        );

        accountTypeRepository.delete(accountType);
    }

    private AccountTypeResponse mapToResponse(
            AccountType accountType
    ) {

        AccountTypeResponse response =
                new AccountTypeResponse();

        response.setId(accountType.getId());

        response.setAccountType(
                accountType.getAccountType()
        );

        response.setDescription(
                accountType.getDescription()
        );

        response.setStatus(
                accountType.getStatus()
        );

        return response;
    }
}
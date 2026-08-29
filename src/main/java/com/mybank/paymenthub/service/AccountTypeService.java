package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.AccountTypeRequestDTO;
import com.mybank.paymenthub.dto.response.AccountTypeResponse;

import java.util.List;

public interface AccountTypeService {

    AccountTypeResponse create(
            AccountTypeRequestDTO request
    );

    AccountTypeResponse getById(Long id);

    List<AccountTypeResponse> getAll();

    AccountTypeResponse update(
            Long id,
            AccountTypeRequestDTO request
    );

    void delete(Long id);
}
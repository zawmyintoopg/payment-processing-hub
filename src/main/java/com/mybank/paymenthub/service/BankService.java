package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.BankRequestDTO;
import com.mybank.paymenthub.dto.response.BankResponse;

import java.util.List;

public interface BankService {

    BankResponse create(BankRequestDTO request);

    BankResponse getById(Long id);

    List<BankResponse> getAll();

    BankResponse update(Long id, BankRequestDTO request);

    void delete(Long id);
}
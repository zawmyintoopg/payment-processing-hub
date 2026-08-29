package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.BankRequestDTO;
import com.mybank.paymenthub.dto.response.BankResponse;
import com.mybank.paymenthub.entity.Bank;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.repository.BankRepository;
import com.mybank.paymenthub.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Override
    public BankResponse create(BankRequestDTO request) {

        // Check duplicate bank code
        if (bankRepository.existsByBankCode(request.getBankCode())) {
            throw new BusinessException(
                    "Bank Code already exists"
            );
        }

        // Check duplicate bank name
        if (bankRepository.existsByBankName(request.getBankName())) {
            throw new BusinessException(
                    "Bank Name already exists"
            );
        }

        Bank bank = new Bank();

        bank.setBankCode(request.getBankCode());
        bank.setBankName(request.getBankName());
        bank.setShortName(request.getShortName());
        bank.setStatus(request.getStatus());

        Bank savedBank =
                bankRepository.save(bank);

        return mapToResponse(savedBank);
    }

    @Override
    public BankResponse getById(Long id) {

        Bank bank =
                bankRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bank Not Found",
                                        "bankId",
                                        id
                                )
                        );

        return mapToResponse(bank);
    }

    @Override
    public List<BankResponse> getAll() {

        return bankRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public BankResponse update(
            Long id,
            BankRequestDTO request
    ) {

        Bank bank =
                bankRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bank Not Found",
                                        "bankId",
                                        id
                                )
                        );

        bank.setBankCode(request.getBankCode());
        bank.setBankName(request.getBankName());
        bank.setShortName(request.getShortName());
        bank.setStatus(request.getStatus());

        Bank updatedBank =
                bankRepository.save(bank);

        return mapToResponse(updatedBank);
    }

    @Override
    public void delete(Long id) {

        Bank bank =
                bankRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Bank Not Found",
                                        "bankId",
                                        id
                                )
                        );

        bankRepository.delete(bank);
    }

    private BankResponse mapToResponse(Bank bank) {

        BankResponse response =
                new BankResponse();

        response.setId(bank.getId());
        response.setBankCode(bank.getBankCode());
        response.setBankName(bank.getBankName());
        response.setShortName(bank.getShortName());
        response.setStatus(bank.getStatus());

        return response;
    }
}
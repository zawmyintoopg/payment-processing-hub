package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantBankAccountRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantBankAccountResponse;
import com.mybank.paymenthub.service.MerchantBankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant-bank-accounts")
@RequiredArgsConstructor
public class MerchantBankAccountController {

    private final MerchantBankAccountService merchantBankAccountService;

    @PostMapping
    public ResponseEntity<MerchantBankAccountResponse> create(
            @Valid @RequestBody MerchantBankAccountRequestDTO request
    ) {

        MerchantBankAccountResponse response =
                merchantBankAccountService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.BankRequestDTO;
import com.mybank.paymenthub.dto.response.BankResponse;
import com.mybank.paymenthub.service.BankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/banks")
public class BankController {
    private final BankService bankService;
    @PostMapping
    public ResponseEntity<BankResponse> create(
            @Valid @RequestBody BankRequestDTO request
    ) {
        return ResponseEntity.ok(
                bankService.create(request)
        );
    }
}

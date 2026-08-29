package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.AccountTypeRequestDTO;
import com.mybank.paymenthub.dto.response.AccountTypeResponse;
import com.mybank.paymenthub.service.AccountTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @PostMapping
    public ResponseEntity<AccountTypeResponse> create(
            @Valid @RequestBody AccountTypeRequestDTO request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        accountTypeService.create(request)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountTypeResponse> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                accountTypeService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<AccountTypeResponse>> getAll() {

        return ResponseEntity.ok(
                accountTypeService.getAll()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountTypeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody AccountTypeRequestDTO request
    ) {

        return ResponseEntity.ok(
                accountTypeService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        accountTypeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
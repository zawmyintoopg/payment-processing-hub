package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantOutletMerchantHistoryResponseDTO;
import com.mybank.paymenthub.service.MerchantOutletService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/merchant-history")
@RequiredArgsConstructor

public class MerchantHistoryController {
private final MerchantOutletService merchantOutletService;
    @GetMapping("/{id}")
    public ResponseEntity<
            ApiResponse<
                    Page<MerchantOutletMerchantHistoryResponseDTO>
                    >
            > getMerchantHistory(
                    @PathVariable Long id,
                    @ParameterObject
                    Pageable pageable
    ) {

        Page<MerchantOutletMerchantHistoryResponseDTO> history =
                merchantOutletService.getMerchantHistory(
                        id,
                        pageable
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Outlet Merchant History Retrieved Successfully",
                        history
                )
        );
    }
}

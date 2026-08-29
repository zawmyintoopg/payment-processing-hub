package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.SettlementProcessResponse;
import com.mybank.paymenthub.service.SettlementService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/settlements")
@RequiredArgsConstructor
@Tag(name = "09 - Settlement")
public class SettlementController {
        private final SettlementService settlementService;
        @PostMapping("/process")
        public SettlementProcessResponse createSettlement(

        ){
            return settlementService.createSettlementProcess();
        }
}

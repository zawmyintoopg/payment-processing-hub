package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.OperationTransactionRequest;
import com.mybank.paymenthub.dto.request.PaymentCallBackRequest;
import com.mybank.paymenthub.dto.request.ReversalRequest;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.OperationTransactionResponse;
import com.mybank.paymenthub.dto.response.PaymentChannelSummaryResponse;
import com.mybank.paymenthub.service.OperationTransactionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/operation-transactions")
@RequiredArgsConstructor
@Tag(name = "08 - Transaction")
public class OperationTransactionController {

    private final OperationTransactionService operationTransactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<OperationTransactionResponse>>
    createOperationTransaction(@RequestBody OperationTransactionRequest request){

        OperationTransactionResponse responseDTO =
                operationTransactionService.create(request);

        return ResponseEntity.status(
                HttpStatus.CREATED
                ).body(
                ApiResponse.success(
                        "200",
                        "Operation Transaction Created Successfully",
                        responseDTO
                )
        );
    }
    @PostMapping("/{transactionNumber}/callback")
    public ResponseEntity<ApiResponse<Void>> paymentCallback(
                                                   @PathVariable String transactionNumber,
                                                   @Valid @RequestBody PaymentCallBackRequest request
    ){
        operationTransactionService.processPaymentCallBack(
                transactionNumber,
                request
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Call Back Payment is successfully",
                        null
                )
        );

    }
    @GetMapping
    public ResponseEntity<ApiResponse<Page<OperationTransactionResponse>>>
        getAllOperationTransaction(
                @RequestParam (required = false) String search,
                @ParameterObject Pageable pageable){

        Page<OperationTransactionResponse> operationTransaction =
        operationTransactionService.getAll(
                search,pageable
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Operation Transaction Retrieved Successfully",
                        operationTransaction
                )
        );
    }
    @GetMapping("/{transactionNumber}")
    public ResponseEntity<ApiResponse<OperationTransactionResponse>>
        getOperationTransactionByTransactionNumber(@PathVariable String transactionNumber){

        OperationTransactionResponse operationTransactionResponse
                = operationTransactionService.getByTransactionNumber(transactionNumber);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Operation Transaction Retrieved Successfully",
                        operationTransactionResponse
                )
        );
    }
    @PostMapping("/{transactionNumber}/reversal")
    public ResponseEntity<ApiResponse> requestReversal(
            @PathVariable String transactionNumber,
            @Valid @RequestBody ReversalRequest request
    ) {

        OperationTransactionResponse response =
                operationTransactionService.requestReversal(
                        transactionNumber,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Payment Reversal requested successfully",
                        response
                )
        );
    }
    @PutMapping("/{transactionNumber}/reversal/callback")
    public ResponseEntity<ApiResponse> reversalCallback(
            @PathVariable String transactionNumber,
            @Valid @RequestBody PaymentCallBackRequest request
    ) {

        OperationTransactionResponse response =
                operationTransactionService.processReversalCallBack(
                        transactionNumber,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Payment Reversal callback processed successfully",
                        response
                )
        );
    }
}

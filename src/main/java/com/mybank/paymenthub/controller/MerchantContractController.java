package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantContractCreateRequest;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantContractResponse;
import com.mybank.paymenthub.service.MerchantContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-contracts")
@PreAuthorize("hasRole('ADMIN')")
@Tag(
        name = "06 - Merchant Contract",
        description = "APIs for managing merchant contracts"
)
@SecurityRequirement(name = "bearerAuth")
public class MerchantContractController {

    private final MerchantContractService merchantContractService;
    @PostMapping
    @Operation(
            summary    = "Create Merchant Contract",
            description = "Creates a new Contract with Merchant"
    )
    public ResponseEntity<ApiResponse<MerchantContractResponse>> createMerchantContract(
            @Valid @RequestBody MerchantContractCreateRequest contractCreateRequest
            ){
        MerchantContractResponse response =
                merchantContractService.createMerchantContract(contractCreateRequest);

        return ResponseEntity.status(
                    HttpStatus.CREATED
                )
                .body(
                        ApiResponse.success(
                                "201",
                                "Merchant Contract Created Successfully",
                                response
                        )
                );

    }
    @Operation(
            summary    = "Update Merchant Contract",
            description = "Updates merchant contract information including the registered merchant"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantContractResponse>>
            updateMerchantContract(
            @PathVariable Long id,
            @Valid @RequestBody MerchantContractCreateRequest request){

        MerchantContractResponse response =
        merchantContractService.updateMerchantContract(id,request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Contract Updated Successfully",
                        response
                )
        );
    }
    @Operation(
            summary    = "Read All Merchant Contract",
            description = "Show All Contract with Merchant"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MerchantContractResponse>>>
        getAllMerchantsContracts(
                @RequestParam (required = false) String search,
                @ParameterObject Pageable pageable
    ){
        Page<MerchantContractResponse> merchantPages =
                merchantContractService.getAllMerchantContracts(
                        search,
                        pageable
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Contract Retrieved Successfully",
                        merchantPages
                )
        );
    }
    @Operation(
            summary    = "Get Merchant By ID with Contract",
            description = "Get Contract with Merchant"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantContractResponse>>
             getMerchantById(@PathVariable Long id){

        MerchantContractResponse response =
                merchantContractService.getMerchantContractById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Contract Retrieved Successfully",
                        response
                )
        );

    }
    @Operation(
            summary = "Deactivate Merchant Contract",
            description = "Deactivates a merchant contract without deleting the database record"
    )
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>>
        deActivateMerchantContract(@PathVariable Long id){

        merchantContractService.deActivateMerchantContract(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Contract Status Deactivated Successfully",
                        null
                )
        );
    }
    @Operation(
            summary = "Activate Merchant Contract",
            description = "Activates a previously deactivated merchant contract"
    )
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>>
        activateMerchantStatus(@PathVariable Long id){

        merchantContractService.activateMerchantContract(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Contract Status Activated Successfully",
                        null
                )
        );
    }
}

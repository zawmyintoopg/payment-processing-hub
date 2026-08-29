package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantOwnerRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantOwnerResponseDTO;
import com.mybank.paymenthub.service.MerchantOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Merchant Owner Management Controller
 *
 * Provides REST APIs for:
 * - Creating merchant owners
 * - Retrieving merchant owner information
 * - Updating merchant owner details
 * - Activating and deactivating merchant owners
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-owners")
@PreAuthorize("hasRole('ADMIN')")
@Tag(

        name = "02 - Merchant Owner",
        description = "APIs for creating, retrieving, updating, activating and deactivating merchant owners"
)
public class MerchantOwnerController {

    private final MerchantOwnerService merchantOwnerService;

    @PostMapping
    @Operation(
            summary = "Create Merchant Owner",
            description = "Creates a new merchant owner with owner information and registration details"

    )
    public ResponseEntity<ApiResponse<MerchantOwnerResponseDTO>> createMerchantOwner(
            @Valid @RequestBody MerchantOwnerRequestDTO requestDTO
    ){
        MerchantOwnerResponseDTO responseDTO =
                merchantOwnerService.createMerchantOwner(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "201",
                                "Merchant Owner Created Successfully",
                                responseDTO)
        );
    }
    @GetMapping
    @Operation(
            summary = "Get Merchant Owner List",
            description = "Retrieves merchant owners with pagination and optional search criteria"
    )
    public ResponseEntity<ApiResponse<Page<MerchantOwnerResponseDTO>>> getAllMerchantOwners(
            @RequestParam(required = false) String search,

            @ParameterObject Pageable pageable
    ){
        Page<MerchantOwnerResponseDTO> responseDTO  =
                merchantOwnerService.getAllMerchantOwners(search,pageable);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Owner List Retrieved Successfully",
                        responseDTO
                )
        );

    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Merchant Owner Details",
            description = "Retrieve merchant owner details by ID"
    )
    public ResponseEntity<ApiResponse<MerchantOwnerResponseDTO>> getMerchantOwnerById(
            @PathVariable("id") Long id
    ){
        MerchantOwnerResponseDTO responseDTO =
                merchantOwnerService.getMerchantOwnerById(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Owner Retrieved Successfully",
                        responseDTO
                )
        );
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Merchant Owner",
            description = "Update merchant owner information by ID"
    )
    public ResponseEntity<ApiResponse<MerchantOwnerResponseDTO>> updateMerchantOwner(
            @PathVariable("id")  Long id, @Valid @RequestBody MerchantOwnerRequestDTO requestDTO
    ){
        MerchantOwnerResponseDTO responseDTO =
                merchantOwnerService.updateMerchantOwner(id,requestDTO);
        return ResponseEntity.ok(
                    ApiResponse.success(
                            "200",
                            "Merchant Owner Updated Successfully",
                            responseDTO
                    )
            );
    }
    @PatchMapping("/{id}/activate")
    @Operation(
            summary = "Activate Merchant Owner",
            description = "Reactivates a previously deactivated merchant owner"
    )
    public ResponseEntity<ApiResponse<Void>> activateMerchantOwner(
            @PathVariable("id") Long id
    ){
        merchantOwnerService.activateMerchantOwner(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Owner Activated Successfully",
                        null
                )
        ) ;
    }
    @PatchMapping("/{id}/deactivate")
    @Operation(
            summary = "Deactivate Merchant Owner",
            description = "Deactivates a merchant owner using soft delete without removing database records"
    )
    public ResponseEntity<ApiResponse<Void>> deactivateMerchantOwner(
            @PathVariable("id") Long id
    ) {
        merchantOwnerService.deactivateMerchantOwner(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Owner Deactivated Successfully",
                        null
                )
        );
    }
}

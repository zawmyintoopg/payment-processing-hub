package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantOwnerRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.service.MerchantOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@CrossOrigin(
        origins = "http://localhost:5175"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-owners")
@PreAuthorize("hasRole('ADMIN')")
@Tag(
        name="Merchant Owners",
        description="Merchant Owner Management APIs"
)
public class MerchantOwnerController {

    private final MerchantOwnerService service;
    //SAVE MERCHANT
    @PostMapping
    @Operation(
            summary = "Create Merchant Owner",
            description = "Create new merchant owner"

    )
    public ResponseEntity<ApiResponse<?>> saveMerchantOwner(@Valid @RequestBody MerchantOwnerRequestDTO requestDTO){

        return ResponseEntity.ok(
                ApiResponse.success("201","Merchant Owner Created Successfully",service.createMerchantOwner(requestDTO))
        );
    }


    //GET ALL MERCHANTS
    @GetMapping
    @Operation(
            summary = "Get Merchant Owner List",
            description = "Retrieve merchant owner list with pagination"
    )
    public ResponseEntity<ApiResponse<?>> getAllMerchantOwners(
            @ParameterObject
            @PageableDefault(
                    page =  0,
                    size =  10,
                    sort = "createdDate",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable
    ){

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Owner List Retrieved Successfully",
                        service.getAllMerchantOwners(pageable)
                )
        );

    }
    // GET MERCHANT BY ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Merchant Owner By ID",
            description = "Retrieve merchant owner details by ID"
    )
    public ResponseEntity<ApiResponse<?>> getMerchantOwnerById(@PathVariable Long id){

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Owner List Retrieved Successfully",
                        service.getMerchantOwnerById(id)
                )
        );
    }
    //UPDATE MERCHANT
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Merchant Owner",
            description = "Update merchant owner information by ID"
    )
    public ResponseEntity<ApiResponse<?>> updateMerchantOwner(@PathVariable Long id, @Valid @RequestBody MerchantOwnerRequestDTO requestDTO){

        return ResponseEntity.ok(
                ApiResponse.success("200","Merchant Owner Updated Successfully",service.updateMerchantOwner(id,requestDTO))
        );
    }

    //DELETE MERCHANT
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Merchant Owner",
            description = "Soft delete merchant owner by ID"
    )
    public ResponseEntity<ApiResponse<?>> deactivateMerchantOwner(@PathVariable Long id){
        service.deactivateMerchantOwner(id);
        return ResponseEntity.ok(

                ApiResponse.success(
                        "200",
                        "Merchant Owner Deactivated Successfully",
                        null
                )

        );
    }
}

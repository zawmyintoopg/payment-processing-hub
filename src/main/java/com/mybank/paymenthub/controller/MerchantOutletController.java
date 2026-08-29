package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import com.mybank.paymenthub.service.MerchantOutletService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-outlets")
@Tag(
        name = "07 - Merchant Outlet",
        description = "APIs for managing merchant outlets"
)
@SecurityRequirement(name = "bearerAuth")
public class MerchantOutletController {
    //CRUD Search , Pagination

    private final MerchantOutletService merchantOutletService;
    //CREATE MERCHANT OUTLET
    @PostMapping
    public ResponseEntity<ApiResponse<MerchantOutletResponseDTO>>
           createMerchantOutlet(
                   @Valid @RequestBody
                   MerchantOutletRequestDTO merchantOutletRequestDTO
    ) {

        MerchantOutletResponseDTO responseDTO =
                merchantOutletService.createMerchantOutlet(merchantOutletRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).
                body(
                        ApiResponse.success(
                                "201",
                                "Merchant Outlet Created Successfully",
                                responseDTO
                        )
                );

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantOutletResponseDTO>>
            updateMerchantOutlet(
                    @PathVariable Long id,
                    @Valid @RequestBody MerchantOutletRequestDTO requestDTO
        ){

        MerchantOutletResponseDTO updated =
                merchantOutletService.updateMerchantOutlet(
                        id,
                        requestDTO
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Outlet Updated Successfully",
                        updated
                )
        );
    }

    // GET MERCHANT OUTLET LIST
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MerchantOutletResponseDTO>>>
            getAllMerchantOutlets(
            @RequestParam(required = false)
                    String search, @ParameterObject  Pageable pageable
    ){

        //Get merchant outlet data data from service layer
         Page<MerchantOutletResponseDTO> responseDTOPage
                 = merchantOutletService.getAllMerchantOutlets
                 (
                     search,
                     pageable
                 );

        //Return to UI
         return ResponseEntity.ok(
                 ApiResponse.success(
                         "200",
                         "Merchant Outlet Retrieved Successfully",
                         responseDTOPage
                 )
         );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantOutletResponseDTO>>
    getMerchantOutletById(@PathVariable Long id){

        MerchantOutletResponseDTO responseDTO =
                merchantOutletService.getMerchantOutletById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Outlet Retrieved Successfully By ID",
                        responseDTO
                )
        );
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>>
            deActivateMerchantOutlet(@PathVariable Long id){

        merchantOutletService.deActivateMerchantOutlet(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Outlet Deactivated Successfully",
                        null
                )
         );
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>>
            activateMerchantOutlet(@PathVariable Long id){

        merchantOutletService.activateMerchantOutlet(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Outlet Activated Successfully",
                        null
                )
        );
    }
}

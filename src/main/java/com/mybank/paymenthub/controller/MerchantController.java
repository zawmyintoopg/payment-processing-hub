package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantResponseDTO;
import com.mybank.paymenthub.service.MerchantService;
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
@RequestMapping("/api/v1/merchants")
@Tag(name = "05 - Merchant")
@SecurityRequirement(name = "bearerAuth")
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public ResponseEntity<ApiResponse<MerchantResponseDTO>>
        createMerchant(
                @Valid @RequestBody MerchantRequestDTO requestDTO
        ){
          MerchantResponseDTO responseDTO =
                  merchantService.createMerchant(requestDTO);

          return ResponseEntity
                  .status(HttpStatus.CREATED)
                  .body(
                      ApiResponse.success(
                              "201",
                              "Merchant Created Successfully",
                              responseDTO
                      )
          );

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantResponseDTO>>
        updateMerchant(@PathVariable Long id,
                        @Valid @RequestBody MerchantRequestDTO requestDTO )
    {
        MerchantResponseDTO responseDTO =
                merchantService.updateMerchant(id,requestDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Updated Successfully",
                        responseDTO
                )
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MerchantResponseDTO>>>
        getAllMerchants(
                @RequestParam (required = false)
                String search,
                @ParameterObject Pageable pageable
    ){
        Page<MerchantResponseDTO> responseDTOPage =
                merchantService.getAllMerchants(
                        search,
                        pageable
                );

        return ResponseEntity.ok(
                    ApiResponse.success(
                            "200",
                            "Merchant Retrieved Successfully",
                            responseDTOPage
                    )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantResponseDTO>>
        getMerchantById(@PathVariable Long id){

        MerchantResponseDTO responseDTO =
                merchantService.getMerchantById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Retrieved Successfully",
                        responseDTO
                )
        );
    }
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>>
    activateMerchant(@PathVariable Long id)
    {

        merchantService.activateMerchant(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Activated Successfully",
                        null
                )
        );
    }
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<Void>>
    deActivateMerchant(@PathVariable Long id)
    {

        merchantService.deactivateMerchant(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Deactivated Successfully",
                        null
                )
        );
    }

}

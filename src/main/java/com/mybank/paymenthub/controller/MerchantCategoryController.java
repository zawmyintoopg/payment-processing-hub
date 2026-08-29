package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.MerchantCategoryRequestDTO;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantCategoryResponseDTO;
import com.mybank.paymenthub.service.MerchantCategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/merchant-category")
@RequiredArgsConstructor
@Tag(name = "04 - Merchant Category")
public class MerchantCategoryController {


    private final MerchantCategoryService merchantCategoryService;

    @PostMapping
    public ResponseEntity<ApiResponse<MerchantCategoryResponseDTO>>
          createMerchantCategory(
                  @RequestBody
                  @Valid
                  MerchantCategoryRequestDTO requestDTO
    ){
        MerchantCategoryResponseDTO responseDTO =
        merchantCategoryService.createMerchantCategory(requestDTO);

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                ApiResponse.success(
                        "201",
                        "Merchant Category Created Successfully",
                        responseDTO
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantCategoryResponseDTO>> updateMerchantCategory(
            @PathVariable Long id,
            @ParameterObject MerchantCategoryRequestDTO merchantCategoryRequestDTO
            ){
        MerchantCategoryResponseDTO savedMerchantCategory =
                merchantCategoryService.updateMerchantCategory(id,merchantCategoryRequestDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Category Updated Successfully",
                        savedMerchantCategory
                )
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<MerchantCategoryResponseDTO>>> getAllMerchantCategory(
    ){
        List<MerchantCategoryResponseDTO> merchantCategoryList =
                      merchantCategoryService.getAllMerchantCategories();

        return ResponseEntity.ok(

                ApiResponse.success(
                        "200",
                        "Merchant Category Retrieved Successfully",
                        merchantCategoryList
                )
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantCategoryResponseDTO>> getMerchantCategoryById(
           @PathVariable Long id
    ){
        MerchantCategoryResponseDTO merchantCategoryResponseDTO =
                merchantCategoryService.getMerchantCategoryById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Category is Retrieved Successfully",
                        merchantCategoryResponseDTO
                )
        );

    }
    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse<Void>> deActivateMerchantSegment(Long id)
    {
        merchantCategoryService.deactivateMerchantCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Category is DeActivated Successfully",
                        null
                )
        );
    }
    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponse<Void>> activateMerchantSegment(Long id)
    {
        merchantCategoryService.deactivateMerchantCategory(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Category is Activated Successfully",
                        null
                )
        );
    }
}

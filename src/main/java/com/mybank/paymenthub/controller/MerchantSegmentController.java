package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.service.MerchantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchant-segments")

public class MerchantSegmentController {
    private final MerchantCategoryService categoryService;

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id){

        categoryService.delete(id);

        return ResponseEntity.ok(
                ApiResponse.success("200","Merchant Owner Deleted Successfully",null)
        );

    }
}

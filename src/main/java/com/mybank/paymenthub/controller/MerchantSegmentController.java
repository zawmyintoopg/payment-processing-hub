package com.mybank.paymenthub.controller;


import com.mybank.paymenthub.dto.request.MerchantSegmentRequest;
import com.mybank.paymenthub.dto.response.ApiResponse;
import com.mybank.paymenthub.dto.response.MerchantSegmentResponse;
import com.mybank.paymenthub.service.MerchantSegmentService;
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
@RequestMapping("/api/v1/merchant-segments")
@Tag(name = "03 - Merchant Segment")
public class MerchantSegmentController {
    private final MerchantSegmentService merchantSegmentService;
    @PostMapping
    public ResponseEntity<ApiResponse<MerchantSegmentResponse>> createMerchantSegment(
            @Valid
            @RequestBody
            MerchantSegmentRequest merchantSegmentRequest
            ){
        MerchantSegmentResponse response = merchantSegmentService.
                createMerchantSegment(merchantSegmentRequest);

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                ApiResponse.success(
                        "201",
                        "Merchant Segment Created Successfully",
                        response
                )
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MerchantSegmentResponse>> updateMerchantSegment(
            @PathVariable Long id,
            @Valid @RequestBody MerchantSegmentRequest merchantSegmentRequest
    ){
        MerchantSegmentResponse merchantSegmentResponse =
        merchantSegmentService.updateMerchantSegment(id,merchantSegmentRequest);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "200",
                        "Merchant Segment is retrieved Successfully",
                        merchantSegmentResponse
                )
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<Page<MerchantSegmentResponse>>> getAllMerchantSegments(
        @RequestParam(required = false) String search,
        @ParameterObject Pageable pageable
    )
    {
        Page<MerchantSegmentResponse> merchantAllList =
                merchantSegmentService.getAllMerchantSegments(
                       search, pageable
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "201",
                        "Merchant Segment Retrieved Successfully",
                        merchantAllList
                )
        );
    }
    @PutMapping("/deactivateMerchantSegment/{id}")
    public ResponseEntity<ApiResponse<Void>> deActivateMerchantSegment(
            Long id
    ){
        merchantSegmentService.deactivateMerchantSegment(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "201",
                        "Merchant Segment Deactivated Successfully",
                        null
                )
        );

    }

    @PutMapping("/activateMerchantSegment/{id}")
    public ResponseEntity<ApiResponse<Void>> activateMerchantSegment(
            Long id
    ){
        merchantSegmentService.activateMerchantSegment(id);
        return ResponseEntity.ok(
                ApiResponse.success(
                        "201",
                        "Merchant Segment Activated Successfully",
                        null
                )
        );

    }

}

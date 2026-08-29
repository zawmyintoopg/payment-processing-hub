package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantSegmentRequest;
import com.mybank.paymenthub.dto.response.MerchantSegmentResponse;
import com.mybank.paymenthub.entity.MerchantSegment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MerchantSegmentService {

    MerchantSegmentResponse createMerchantSegment(
            MerchantSegmentRequest request
    );
    MerchantSegmentResponse updateMerchantSegment(
            Long id,MerchantSegmentRequest request
    );
    Page<MerchantSegmentResponse> getAllMerchantSegments(
            String search,
            Pageable pageable
    );
    MerchantSegmentResponse getMerchantSegmentById(
            Long id
    );
    void deactivateMerchantSegment(Long id);
    void activateMerchantSegment(Long id);
}

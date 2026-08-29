package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantSegmentRequest;
import com.mybank.paymenthub.dto.response.MerchantSegmentResponse;
import com.mybank.paymenthub.entity.MerchantSegment;
import com.mybank.paymenthub.enums.Status;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantSegmentMapper;
import com.mybank.paymenthub.repository.MerchantSegmentRepository;
import com.mybank.paymenthub.service.MerchantSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MerchantSegmentServiceImpl
        implements MerchantSegmentService {

    private final MerchantSegmentRepository merchantSegmentRepository;
    private final MerchantSegmentMapper merchantSegmentMapper;

    @Override
    public MerchantSegmentResponse createMerchantSegment(
            MerchantSegmentRequest request
    ){
        if(
            merchantSegmentRepository.existsByMerchantSegmentCode(
                request.getMerchantSegmentCode()
        )){
            throw new RuntimeException(
                    "Merchant Segment Code is Already Exist !"
            );
        }

        if(
            merchantSegmentRepository.existsByMerchantSegmentName(
                    request.getMerchantSegmentName()
            )
        ){
            throw new RuntimeException(
                    "Merchant Segment Name is Already Exist !"
            );
        }

        MerchantSegment savedMerchantSegment  =
                merchantSegmentMapper.toEntity(request);
        MerchantSegment saved =
                merchantSegmentRepository.save(savedMerchantSegment);
        return merchantSegmentMapper.toResponse(saved);

    }

    @Override
    public MerchantSegmentResponse updateMerchantSegment(
            Long id,MerchantSegmentRequest request
    )
    {
        MerchantSegment merchantSegment =
                findByMerchantSegmentId(id);

        merchantSegmentMapper.updateEntity(request,merchantSegment);

        return merchantSegmentMapper.toResponse(merchantSegment);

    }
    @Override
    public Page<MerchantSegmentResponse> getAllMerchantSegments(
            String search,
            Pageable pageable
    ){
        Page<MerchantSegment> merchantSegment;

        if(search == null || search.isBlank()){
            merchantSegment =
                    merchantSegmentRepository.findAll(pageable);
        }
        else{
            merchantSegment =
                    merchantSegmentRepository.searchMerchantSegmentCode(search,pageable);
        }

        return merchantSegment.map(
                merchantSegmentMapper::toResponse
        );
    }
    public MerchantSegmentResponse getMerchantSegmentById(
            Long id
    ){
        MerchantSegment merchantSegment = findByMerchantSegmentId(id);

        return merchantSegmentMapper.toResponse(merchantSegment);
    }
    @Override
    public void deactivateMerchantSegment(Long id){

        MerchantSegment merchantSegment = findByMerchantSegmentId(id);
        merchantSegment.setStatus(
                Status.INACTIVE
        );
    }
    @Override
    public void activateMerchantSegment(Long id){

        MerchantSegment merchantSegment = findByMerchantSegmentId(id);
        merchantSegment.setStatus(
                Status.ACTIVE
        );
    }

    private MerchantSegment findByMerchantSegmentId(Long id){

       return merchantSegmentRepository.findById(id).
               orElseThrow(() -> new ResourceNotFoundException(
                       "Merchant Segment Id Not Found",
                       "merchantSegmentId",
                       id
               )
       );

    }
}

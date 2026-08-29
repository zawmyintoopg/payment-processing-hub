package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantContractCreateRequest;
import com.mybank.paymenthub.dto.response.MerchantContractResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MerchantContractService {

    MerchantContractResponse createMerchantContract(
            MerchantContractCreateRequest request
    );

    MerchantContractResponse updateMerchantContract(
            Long id, MerchantContractCreateRequest request
    );

    Page<MerchantContractResponse> getAllMerchantContracts(
           String search,
           Pageable pageable
    );

    MerchantContractResponse getMerchantContractById(
            Long id
    );

    void deActivateMerchantContract(
            Long id
    );

    void activateMerchantContract(
            Long id
    );

}

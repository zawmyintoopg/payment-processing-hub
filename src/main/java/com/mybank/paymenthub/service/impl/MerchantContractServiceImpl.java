package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantContractCreateRequest;
import com.mybank.paymenthub.dto.response.MerchantContractResponse;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantContract;
import com.mybank.paymenthub.enums.ContractStatus;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantContractMapper;
import com.mybank.paymenthub.repository.MerchantContractRepository;
import com.mybank.paymenthub.repository.MerchantRepository;
import com.mybank.paymenthub.service.MerchantContractService;
import com.mybank.paymenthub.service.NumberSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MerchantContractServiceImpl implements MerchantContractService {

    private final MerchantContractRepository merchantContractRepository;
    private final MerchantRepository merchantRepository;
    private final NumberSequenceService numberSequenceService;
    private final MerchantContractMapper merchantContractMapper;

    @Override
    public MerchantContractResponse createMerchantContract(
            MerchantContractCreateRequest request
    ){
        Merchant merchant =
            findMerchantById(request.getMerchantId());

        if(merchant.getStatus() != MerchantStatus.ACTIVE){
            throw new BusinessException(
                    "Merchant must be active"
            );
        }

        checkDuplicateActiveContract(request);

        checkContractDates(request);

        String contractNumber =
                numberSequenceService.generateContractNumber();

        MerchantContract entity =
                    merchantContractMapper.toEntity(request);

        entity.setContractNumber(contractNumber);
        entity.setMerchant(merchant);

        MerchantContract saved =
                merchantContractRepository.save(entity);

        return merchantContractMapper.toResponse(saved);

    }
    @Override
    public MerchantContractResponse updateMerchantContract(
            Long id,
            MerchantContractCreateRequest request
    ) {

        // 01. Find existing contract
        MerchantContract merchantContract =
                findMerchantContractById(id);

        // 02. Find new merchant
        Merchant newMerchant =
                findMerchantById(request.getMerchantId());

        // 03. New merchant must be active
        if (newMerchant.getStatus() != MerchantStatus.ACTIVE) {
            throw new BusinessException(
                    "Merchant must be active"
            );
        }

        // 04. Check only when merchant is changed
        if (!merchantContract.getMerchant().getId()
                .equals(newMerchant.getId())) {

            boolean exists =
                    merchantContractRepository
                            .existsByMerchantIdAndStatus(
                                    newMerchant.getId(),
                                    ContractStatus.ACTIVE
                            );

            if (exists) {
                throw new BusinessException(
                        "New Merchant already has active contract"
                );
            }

            merchantContract.setMerchant(newMerchant);
        }

        // 05. Update other fields
        merchantContractMapper.updateEntity(
                merchantContract,
                request
        );

        // 06. Save
        MerchantContract updated =
                merchantContractRepository.save(
                        merchantContract
                );

        // 07. Response
        return merchantContractMapper.toResponse(updated);
    }
    @Override
    public Page<MerchantContractResponse> getAllMerchantContracts(
            String search,
            Pageable pageable
    ){
        Page<MerchantContract> merchantContractPage;

        if(search == null || search.isBlank()){

            merchantContractPage = merchantContractRepository.findAll(pageable);
        }
        else{
            merchantContractPage = merchantContractRepository.searchMerchantContracts(
                    search,pageable
            );
        }

        return merchantContractPage.map(
                merchantContractMapper::toResponse
        );
    }
   @Override
    public MerchantContractResponse getMerchantContractById(
            Long id
    ){
        MerchantContract merchantContract = findMerchantContractById(id);

        return merchantContractMapper.toResponse(merchantContract);
    }

    public void deActivateMerchantContract(
            Long id
    ){
        MerchantContract merchantContract = findMerchantContractById(id);
        if(merchantContract.getStatus() == ContractStatus.INACTIVE){
            throw new  BusinessException(
                "Merchant Contract Status is Already Deactivated"
            );
        }
        merchantContract.setStatus(ContractStatus.INACTIVE);
    }
    @Override
    public void activateMerchantContract(
            Long id
    ){
        MerchantContract merchantContract = findMerchantContractById(id);

        if(merchantContract.getStatus() ==ContractStatus.ACTIVE){
            throw new BusinessException(
                    "Merchant Contract Status is Already Active"
            );
        }

        merchantContract.setStatus(ContractStatus.ACTIVE);
    }

    private void checkDuplicateActiveContract(
            MerchantContractCreateRequest request
    ){

        boolean exists =
                merchantContractRepository.existsByMerchantIdAndStatus(
                request.getMerchantId(),
                ContractStatus.ACTIVE
        );
        if (exists){
           throw new BusinessException(
                   "Merchant already has active contract"
           );
        }
    }

    private Merchant findMerchantById(Long merchantId){

        return
                merchantRepository.findById(merchantId)
                        .orElseThrow(() ->
                          new ResourceNotFoundException
                                        (
                                            "Merchant Not Found" ,
                                             "merchantId",
                                              merchantId
                                        )
                        );
    }
    private MerchantContract findMerchantContractById(Long id){

        return
                merchantContractRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException
                                        (
                                                "Merchant Contract Not Found" ,
                                                "id",
                                                id
                                        )
                        );
    }
    private void checkContractDates(
            MerchantContractCreateRequest request
    ){
        if(request.getContractDate().isAfter(
                request.getContractStartDate()
        )){
            throw new BusinessException(
                    "Contract Date Can not be after Contract Start Date"
            );
        }
        if(request.getContractStartDate().isAfter(
                request.getContractEndDate()
        )){
            throw new BusinessException(
                    "Contract Start Date Can not After Contract End Date"
            );
        }
    }


}

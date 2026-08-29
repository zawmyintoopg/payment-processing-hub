package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantOutletRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletMerchantHistoryResponseDTO;
import com.mybank.paymenthub.dto.response.MerchantOutletResponseDTO;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.entity.MerchantOutletMerchantHistory;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.enums.OutletStatus;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantOutletMapper;
import com.mybank.paymenthub.mapper.MerchantOutletMerchantHistoryMapper;
import com.mybank.paymenthub.repository.MerchantOutletMerchantHistoryRepository;
import com.mybank.paymenthub.repository.MerchantOutletRepository;
import com.mybank.paymenthub.repository.MerchantRepository;
import com.mybank.paymenthub.service.MerchantOutletService;
import com.mybank.paymenthub.service.NumberSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Transactional
public class MerchantOutletServiceImpl
        implements MerchantOutletService {

    private final MerchantOutletRepository merchantOutletRepository;
    private final NumberSequenceService numberSequenceService;
    private final MerchantRepository merchantRepository;
    private final MerchantOutletMapper merchantOutletMapper;
    private final MerchantOutletMerchantHistoryRepository merchantOutletMerchantHistoryRepository;
    private final MerchantOutletMerchantHistoryMapper merchantOutletMerchantHistoryMapper;
    //create merchant outlet
    @Override
    public MerchantOutletResponseDTO createMerchantOutlet(
            MerchantOutletRequestDTO requestDTO
    ){

        Merchant merchant = findMerchantId(requestDTO.getMerchantId());

        if (merchant.getStatus() == MerchantStatus.INACTIVE){
            throw new BusinessException(
                    "Related Merchant is Not Active"
            );
        }

        checkDuplicateForCreate(requestDTO);

        MerchantOutlet merchantOutlet  =
                merchantOutletMapper.toEntity(requestDTO);
        String outletNumber =
                numberSequenceService.generateOutletNumber();

        merchantOutlet.setMerchant(merchant);
        merchantOutlet.setOutletNumber(outletNumber);

        MerchantOutlet savedMerchantOutlet =
                merchantOutletRepository.save(merchantOutlet);


        // Create initial history
        MerchantOutletMerchantHistory history =
                MerchantOutletMerchantHistory.builder()
                        .merchantOutlet(savedMerchantOutlet)
                        .merchant(merchant)
                        .effectiveFrom(LocalDateTime.now())
                        .effectiveTo(null)
                        .changeReason("Initial merchant assignment")
                        .build();

        merchantOutletMerchantHistoryRepository.save(
                history
        );

        return merchantOutletMapper.toResponse(savedMerchantOutlet);
    }

    @Override
    public MerchantOutletResponseDTO updateMerchantOutlet(
            Long id,
            MerchantOutletRequestDTO requestDTO
    ) {

        // 01. Find existing outlet
        MerchantOutlet merchantOutlet =
                findByMerchantOutletId(id);

        // 02. Find new merchant
        Merchant newMerchant =
                findMerchantId(requestDTO.getMerchantId());

        // 03. Check new merchant status
        if (newMerchant.getStatus() == MerchantStatus.INACTIVE) {
            throw new BusinessException(
                    "Related Merchant is Not Active"
            );
        }

        // 04. Check outlet name duplicate
        checkDuplicateForUpdate(
                id,
                requestDTO
        );

        // 05. Check whether merchant changed
        Merchant oldMerchant =
                merchantOutlet.getMerchant();

        if (!oldMerchant.getId()
                .equals(newMerchant.getId())) {

            changeMerchantWithHistory(
                    merchantOutlet,
                    oldMerchant,
                    newMerchant
            );
        }

        // 06. Update outlet information
        merchantOutletMapper.updateEntity(
                requestDTO,
                merchantOutlet
        );

        // 07. Save
        MerchantOutlet updated =
                merchantOutletRepository.save(
                        merchantOutlet
                );

        // 08. Return
        return merchantOutletMapper.toResponse(
                updated
        );
    }
    @Override
    public Page<MerchantOutletResponseDTO> getAllMerchantOutlets(
            String search, //user search keyword  search ="Yangon"
            Pageable pageable //pagination information
    ){
        Page<MerchantOutlet> merchantOutlets; // page object
        //Page - pagination container
        //MerchantOutlet - entity object type

        if (search == null || search.isBlank()){
            merchantOutlets = merchantOutletRepository.findAll(pageable);
        }
        else {
            merchantOutlets = merchantOutletRepository.
                    searchMerchantOutlet(search,pageable);
        }
        // Convert entity to DTO
        return merchantOutlets.map(
                merchantOutletMapper::toResponse
        );
    }
    @Override
    public MerchantOutletResponseDTO getMerchantOutletById(
            Long id
    ){
        MerchantOutlet merchantOutlet =  findByMerchantOutletId(id);

        return merchantOutletMapper
                .toResponse(merchantOutlet);
    }

    @Override
    public void activateMerchantOutlet(Long id){

        MerchantOutlet merchantOutlet = findByMerchantOutletId(id);

        if (merchantOutlet.getStatus() == OutletStatus.ACTIVE){
            throw new RuntimeException("Merchant Outlet Already Active");
        }
        merchantOutlet.setStatus(OutletStatus.ACTIVE);
        merchantOutletRepository.save(merchantOutlet);
    }
    @Override
    public Page<MerchantOutletMerchantHistoryResponseDTO>
    getMerchantHistory(
            Long merchantOutletId,
            Pageable pageable
    ) {

        findByMerchantOutletId(merchantOutletId);

        return merchantOutletMerchantHistoryRepository
                .findByMerchantOutletId(
                        merchantOutletId,
                        pageable
                )
                .map(
                        merchantOutletMerchantHistoryMapper
                                ::toResponse
                );
    }
    @Override
    public void deActivateMerchantOutlet(Long id){

        MerchantOutlet merchantOutlet = findByMerchantOutletId(id);
        if (merchantOutlet.getStatus() == OutletStatus.INACTIVE){
            throw new BusinessException("Merchant Outlet Already Deactivated");
        }
        merchantOutlet.setStatus(OutletStatus.INACTIVE);
        merchantOutletRepository.save(merchantOutlet);
    }

    private void checkDuplicateForCreate(
            MerchantOutletRequestDTO requestDTO
    ){

        if (requestDTO.getOutletName() != null &&
           merchantOutletRepository.existsByOutletName(requestDTO.getOutletName())){
            throw new BusinessException("Outlet Name Already Exist");
        }
    }

    private void checkDuplicateForUpdate(
            Long id,
            MerchantOutletRequestDTO requestDTO
    ){

        if (requestDTO.getOutletName() != null &&
        merchantOutletRepository.existsByOutletNameAndIdNot(requestDTO.getOutletName(),id)){
            throw new DuplicateException("Outlet Name Already Exist");
        }
    }
    private Merchant findMerchantId(Long merchantId){

        return merchantRepository
                .findById(merchantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Merchant Not Found",
                        "merchantId",
                        merchantId
                )
        );
    }
    private MerchantOutlet findByMerchantOutletId(Long merchantOutletId){

        return merchantOutletRepository
                .findById(merchantOutletId)
                .orElseThrow(() ->new ResourceNotFoundException(
                        "Merchant Outlet Not Found",
                        "merchantOutletId",
                        merchantOutletId
                )
        );
    }
    private void changeMerchantWithHistory(
            MerchantOutlet merchantOutlet,
            Merchant oldMerchant,
            Merchant newMerchant
    ) {

        LocalDateTime now =
                LocalDateTime.now();

        // 01. Close old relationship
        merchantOutletMerchantHistoryRepository.findByMerchantOutletIdAndEffectiveToIsNull(
                        merchantOutlet.getId()
                )
                .ifPresent(history -> {

                    history.setEffectiveTo(now);

                    history.setChangeReason(
                            "Merchant changed"
                    );

                    merchantOutletMerchantHistoryRepository
                            .save(history);
                });

        // 02. Create new relationship history
        MerchantOutletMerchantHistory newHistory =
                MerchantOutletMerchantHistory.builder()
                        .merchantOutlet(merchantOutlet)
                        .merchant(newMerchant)
                        .effectiveFrom(now)
                        .effectiveTo(null)
                        .changeReason("Merchant changed")
                        .build();

        merchantOutletMerchantHistoryRepository
                .save(newHistory);

        // 03. Update current merchant
        merchantOutlet.setMerchant(newMerchant);
    }

}

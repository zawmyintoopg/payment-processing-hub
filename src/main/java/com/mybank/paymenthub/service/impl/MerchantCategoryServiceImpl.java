package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantCategoryRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantCategoryResponseDTO;
import com.mybank.paymenthub.entity.MerchantCategory;
import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.MerchantCategoryMapper;
import com.mybank.paymenthub.repository.MerchantCategoryRepository;
import com.mybank.paymenthub.service.MerchantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MerchantCategoryServiceImpl
        implements MerchantCategoryService {

    private final MerchantCategoryRepository categoryRepository;
    private final MerchantCategoryMapper merchantCategoryMapper;
    @Override
    public MerchantCategoryResponseDTO createMerchantCategory(
            MerchantCategoryRequestDTO requestDTO
    ){
        checkDuplicateCategoryCodeAndName(
                null,
                "create",
                requestDTO
        );
        MerchantCategory savedMerchantCategory =  categoryRepository.save(
                merchantCategoryMapper.toEntity(requestDTO)
        );

        return merchantCategoryMapper.toResponse(savedMerchantCategory);
    }

   @Override
    public MerchantCategoryResponseDTO updateMerchantCategory(
            Long id,
            MerchantCategoryRequestDTO requestDTO
    ){
        MerchantCategory merchantCategory =
                categoryRepository.findById(
                id
                ).orElseThrow(() ->
                                new ResourceNotFoundException(
                                    "Merchant Category ID Not Found",
                                    "merchant_category_id",
                                    id
                )
        );
        checkDuplicateCategoryCodeAndName(
                id,
                "update",
                requestDTO
        );

        merchantCategoryMapper.updateEntity(requestDTO,merchantCategory);
        MerchantCategory saved =
        categoryRepository.save(merchantCategory);

        return merchantCategoryMapper.toResponse(saved);
    }

    @Override
    public List<MerchantCategoryResponseDTO> getAllMerchantCategories(){

        List<MerchantCategory> merchantCategories =
                categoryRepository.findAll();

        return merchantCategories.stream()
                .map(merchantCategoryMapper::toResponse)
                .toList();
    }

    @Override
    public MerchantCategoryResponseDTO getMerchantCategoryById(Long id){

        MerchantCategory merchantCategory =
               findByCategoryId(id);
       return merchantCategoryMapper
               .toResponse(merchantCategory);
    }

    @Override
    public void deactivateMerchantCategory(
            Long id
    ){
        MerchantCategory merchantCategory = findByCategoryId(id);

        merchantCategory.setMerchantCategoryStatus(
                MerchantCategoryStatus.INACTIVE
        );
    }
    @Override
    public void activateMerchantCategory(
            Long id
    ){
        MerchantCategory merchantCategory = findByCategoryId(id);

        merchantCategory.setMerchantCategoryStatus(
                MerchantCategoryStatus.ACTIVE
        );
    }
   // CHECK DUPLICATE SECTION

    private void checkDuplicateCategoryCodeAndName(
        Long id,
        String action,
        MerchantCategoryRequestDTO requestDTO
    ){
        if("create".equalsIgnoreCase(action)){

            if(categoryRepository.existsByCategoryCode(requestDTO.getCategoryCode())){
                throw new DuplicateException("Category Code Already Exist !");
            }

            if(categoryRepository.existsByCategoryName(requestDTO.getCategoryName())){
                throw new DuplicateException("Category Name Already Exist !");
            }
        }
        if("update".equalsIgnoreCase(action)){

            if(categoryRepository.existsByCategoryCodeAndId(
                    requestDTO.getCategoryCode(),
                    id
            )){
                throw new DuplicateException("Category Code Already Exist !");
            }
            if(categoryRepository.existsByCategoryNameAndId(
                    requestDTO.getCategoryName(),
                    id
            )){
                throw new DuplicateException("Category Name Already Exist !");
            }
        }
    }

    private MerchantCategory findByCategoryId(Long id){

        return categoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                        "CategoryID Not Found",
                        "categoryId",
                        id
                  )
        );
    }
}

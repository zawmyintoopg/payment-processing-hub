package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.MerchantCategoryRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantCategoryResponseDTO;
import com.mybank.paymenthub.entity.MerchantCategory;
import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import com.mybank.paymenthub.exception.DuplicateException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.repository.MerchantCategoryRepository;
import com.mybank.paymenthub.service.MerchantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MerchantCategoryServiceImpl implements MerchantCategoryService {

    private final MerchantCategoryRepository categoryRepository;
    //RESPONSE FUNCTION
    private MerchantCategoryResponseDTO mapToResponse(MerchantCategory category){

        MerchantCategoryResponseDTO response = new MerchantCategoryResponseDTO();

        response.setId(category.getId());
        response.setCategoryCode(category.getCategoryCode());
        response.setCategoryName(category.getCategoryName());
        response.setDescription(category.getDescription());
        response.setStatus(category.getMerchantCategoryStatus());

        return response;
    }
   // CHECK DUPLICATE SECTION
    private void checkDuplicate(MerchantCategoryRequestDTO requestDTO){
            if(categoryRepository.existsByCategoryCode(requestDTO.getCategoryCode())){
                throw new DuplicateException("Category Code Already Exist !");
            }

            if(categoryRepository.existsByCategoryName(requestDTO.getCategoryName())){
                throw new DuplicateException("Category Name Already Exist !");
            }
    }
    // CATEGORY ALL LIST
    public List<MerchantCategoryResponseDTO> getAllMerchantCategories(){

        return categoryRepository.findAll().stream().map(this::mapToResponse).toList();

    }
    //GET FUNCTION
    private MerchantCategory findCategory(Long id){
        return categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Merchant Category","id",id));
    }
    // CATEGORY BY ID SEARCH
    public MerchantCategoryResponseDTO getMerchantCategoryById(Long id){

        return mapToResponse(findCategory(id));
    }
    // CREATE CATEGORY
    public MerchantCategoryResponseDTO createMerchantCategory(MerchantCategoryRequestDTO requestDTO){
        checkDuplicate(requestDTO);

        MerchantCategory merchantCategory = new MerchantCategory();

        merchantCategory.setCategoryCode(requestDTO.getCategoryCode());
        merchantCategory.setCategoryName(requestDTO.getCategoryName());
        merchantCategory.setDescription(requestDTO.getDescription());
        merchantCategory.setMerchantCategoryStatus(requestDTO.getStatus());

        return mapToResponse(categoryRepository.save(merchantCategory));
    }
    // UPDATE CATEGORY
    public MerchantCategoryResponseDTO updateMerchantCategory(Long id,MerchantCategoryRequestDTO requestDTO){
        MerchantCategory category = categoryRepository.findById(id).orElseThrow(() -> new DuplicateException("Category ID not found"));
        if(categoryRepository.existsByCategoryCodeAndIdNot(requestDTO.getCategoryCode(),id)){
            throw new DuplicateException("Category Code Already Exist !");
        }

        if(categoryRepository.existsByCategoryNameAndIdNot(requestDTO.getCategoryName(),id)){
            throw new DuplicateException("Category Name is Already Exist !");
        }
        category.setCategoryCode(requestDTO.getCategoryCode());
        category.setCategoryName(requestDTO.getCategoryName());
        category.setDescription(requestDTO.getDescription());
        category.setMerchantCategoryStatus(requestDTO.getStatus());

        return mapToResponse(categoryRepository.save(category));

    }
    //DELETE CATEGORY
    @Override
    public void delete(Long id){
        //search record by id
       MerchantCategory category =  findCategory(id);
        //check status
       if(category.getMerchantCategoryStatus() == MerchantCategoryStatus.ACTIVE){
           throw new DuplicateException("Merchant Status is Already Inactive");
       }
    }
}

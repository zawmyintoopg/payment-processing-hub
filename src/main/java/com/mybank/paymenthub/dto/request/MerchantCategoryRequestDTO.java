package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantCategoryRequestDTO {

        @NotBlank(message = "Category Code can not be blank !")
        private String categoryCode;

        @NotBlank(message = "Category Name can not be blank !")
        private String categoryName;

        private String description;

        private MerchantCategoryStatus status;

    }


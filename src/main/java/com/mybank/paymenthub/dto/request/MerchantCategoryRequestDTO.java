package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantCategoryRequestDTO {

        @NotBlank(
                message = "Category Code is required!"
        )
        private String categoryCode;

        @NotBlank(
                message = "Category Name is required !"
        )
        private String categoryName;

        private String description;
    }


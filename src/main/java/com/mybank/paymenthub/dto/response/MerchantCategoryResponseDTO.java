package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantCategoryResponseDTO {
    private Long id;
    private String categoryCode;
    private String categoryName;
    private String description;
    private MerchantCategoryStatus status;
}

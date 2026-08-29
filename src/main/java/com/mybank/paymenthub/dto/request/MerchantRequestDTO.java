package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data
public class MerchantRequestDTO {

        @NotBlank(
                message = "Merchant Name is required"
        )
        @Size(
                max=150,
                message = "Merchant Name exceeds 150 characters"
        )
        private String merchantName;

        @NotNull(
                message = "Business Registration Date is required"
        )
        private LocalDate businessRegistrationDate;

        @NotNull(
                message = "Merchant Owner ID is required"
        )
        private Long merchantOwnerId;

        @NotNull(
                message = "Merchant Segment ID is required"
        )
        private Long merchantSegmentId;

        @NotNull(
                message = "Merchant Category ID is required"
        )
        private Long merchantCategoryId;

}


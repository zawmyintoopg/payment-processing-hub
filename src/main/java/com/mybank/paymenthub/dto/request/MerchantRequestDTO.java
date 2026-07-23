package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.MerchantStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRequestDTO {

        @NotBlank(message = "Merchant Name should not be blank")
        @Size(max=150,message = "Merchant Name should not be greater than 150")
        private String merchantName;

        @NotBlank(message = "Merchant Registration No should not be blank")
        private String  merchantRegistrationNo;

        @NotNull(message = "Business Registration Date should not be blank")
        private LocalDate businessRegistrationDate;

        @NotNull(message = "Merchant Owner should not be blank")
        private Long merchantOwnerId;

        @NotNull(message = "Merchant Segment should not be blank")
        private Long merchantSegmentId;

        @NotNull(message = "Merchant Category should not be blank")
        private Long merchantCategoryId;

        @NotNull(message = "Merchant Status should not be blank")
        private MerchantStatus status;

}


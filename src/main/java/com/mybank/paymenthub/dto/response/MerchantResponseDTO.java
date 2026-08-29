package com.mybank.paymenthub.dto.response;


import com.mybank.paymenthub.enums.MerchantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MerchantResponseDTO {
    private Long id;
    private String merchantNumber;
    private String merchantName;
    private LocalDate businessRegistrationDate;
    private Long merchantOwnerId;
    private String merchantOwnerName;
    private Long merchantSegmentId;
    private String merchantSegmentName;
    private Long merchantCategoryId;
    private String merchantCategoryName;
    private MerchantStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}



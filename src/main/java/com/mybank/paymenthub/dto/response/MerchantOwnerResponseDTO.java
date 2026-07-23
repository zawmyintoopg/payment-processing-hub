package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.MerchantOwnerType;
import com.mybank.paymenthub.enums.MerchantStatus;
import com.mybank.paymenthub.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MerchantOwnerResponseDTO {
    private Long id;
    private String ownerNumber;
    private String ownerName;
    private MerchantOwnerType ownerType;
    private String registrationNo;
    private String phone;
    private String email;
    private MerchantStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

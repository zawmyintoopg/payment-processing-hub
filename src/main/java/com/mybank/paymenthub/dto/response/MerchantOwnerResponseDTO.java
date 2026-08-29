package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.MerchantOwnerType;
import com.mybank.paymenthub.enums.MerchantStatus;
import lombok.*;
import java.time.LocalDateTime;
/**
 * Response DTO for Merchant Owner APIs.
 *
 * This DTO is used to return merchant owner information
 * from backend services to API clients.
 *
 * It separates API response data from the database entity
 * to avoid exposing entity structure directly.
 */
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

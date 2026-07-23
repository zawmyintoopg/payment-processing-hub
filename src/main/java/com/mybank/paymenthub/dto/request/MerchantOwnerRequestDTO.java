package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.MerchantOwnerType;
import com.mybank.paymenthub.enums.MerchantStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class MerchantOwnerRequestDTO {

    @NotBlank(message = "Owner Name can not be blank")
    @Size( max= 100 , message = "Owner Name is maximum 100 characters")
    private String ownerName;

    @NotNull(message = "Merchant Owner type is required")
    private MerchantOwnerType ownerType;

    @Size( max= 50 , message = "Owner Registration No is maximum 50 characters")
    private String registrationNo;

    @NotBlank(message = "Owner Phone is required")
    @Size( max= 20 , message = "Owner phone is maximum 20 characters")
    private String phone;

    @Size( max= 100 , message = "Owner Email is maximum 100 characters")
    @Email(message = "Invalid Email")
    private String email;
    private MerchantStatus status;

}

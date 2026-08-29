package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.MerchantOwnerType;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * Request DTO for creating and updating Merchant Owner information.
 *
 * Used to validate client input before processing
 * merchant owner business operations.
 */
@Data
public class MerchantOwnerRequestDTO {

    @NotBlank(
            message = "Owner Name can not be blank"
    )
    @Size(
            max= 100,
            message = "Owner Name is maximum 100 characters"
    )
    private String ownerName;

    @NotNull(
            message = "Owner type is required"
    )
    private MerchantOwnerType ownerType;

    @Size(
            max= 50,
            message = "Owner Registration No is maximum 50 characters"
    )
    private String registrationNo;

    @NotBlank(
            message = "Owner Phone is required"
    )
    @Pattern(
            regexp = "^[0-9+\\- ]{7,20}$",
            message = "Invalid phone number format"

    )
    @Size(
            max= 20,
            message = "Owner Phone is maximum 20 characters"
    )
    private String phone;

    @Size(
            max= 100,
            message = "Owner Email is maximum 100 characters"
    )
    @Email(message = "Invalid Email")
    private String email;
}

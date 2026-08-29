package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MerchantOutletRequestDTO{
    @NotBlank(
            message = "Outlet Name is required"
    )
    private String outletName;

    @NotBlank(
            message="Address is required"
    )
    private String address;

    @NotBlank(
            message="City is required"
    )
    private String city;

    @NotBlank(
            message = "Phone is required"
    )
    @Pattern(
            regexp = "^(09|\\+959)[0-9]{7,9}$",
            message = "Invalid phone number"
    )
    private String phone;

    @NotNull(
            message="Merchant Id is required"
    )
    private Long merchantId;
}

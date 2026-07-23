package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.entity.BaseEntity;
import com.mybank.paymenthub.enums.MerchantStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantOutletRequestDTO extends BaseEntity {
    @NotBlank(message = "Outlet name should not be blank")
    private String outletName;

    @NotBlank(message="Address should not be blank")
    private String address;

    private String city;

    @NotBlank(message = "Phone should not be blank")
    private String phone;

    @NotNull(message="Merchant name should not be blank")
    private Long merchantId;

    @NotNull(message="Status should not be blank")
    private MerchantStatus status;
}

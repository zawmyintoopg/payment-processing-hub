package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Username can not be blank")
    @Size(max = 20, message = "User name can not be greater than 20 Characters")
    private String username;

    @NotBlank(message = "Password can not be blank")
    @Size(max = 20, message = "Password can not be greater than 20 Characters")
    private String password;

}

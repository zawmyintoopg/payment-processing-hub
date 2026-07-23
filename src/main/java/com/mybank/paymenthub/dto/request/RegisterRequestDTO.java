package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.Role;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    private String username;

    private String password;

    private Role role;

}
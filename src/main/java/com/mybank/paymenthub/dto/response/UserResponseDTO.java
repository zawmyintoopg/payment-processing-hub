package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.Role;
import com.mybank.paymenthub.enums.Status;
import com.mybank.paymenthub.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Random;

@Getter
@AllArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String username;
    private Role role;
    private UserStatus status;
}
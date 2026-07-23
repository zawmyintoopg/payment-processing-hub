package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
        private String token;
        private String username;
        private Role role;
}

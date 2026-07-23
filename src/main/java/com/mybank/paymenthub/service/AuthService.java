package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.LoginRequestDTO;
import com.mybank.paymenthub.dto.request.RegisterRequestDTO;
import com.mybank.paymenthub.dto.response.LoginResponseDTO;

public interface AuthService {

    void register(RegisterRequestDTO dto);

    LoginResponseDTO login(LoginRequestDTO requestDTO);
}

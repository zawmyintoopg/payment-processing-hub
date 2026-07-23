package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.UserRequestDTO;
import com.mybank.paymenthub.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO request);
}

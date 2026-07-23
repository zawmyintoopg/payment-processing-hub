package com.mybank.paymenthub.controller;


import com.mybank.paymenthub.dto.request.UserRequestDTO;
import com.mybank.paymenthub.dto.response.UserResponseDTO;
import com.mybank.paymenthub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @PostMapping
    public UserResponseDTO create(
            @Valid @RequestBody UserRequestDTO request
    ){
        return userService.createUser(request);
    }
}
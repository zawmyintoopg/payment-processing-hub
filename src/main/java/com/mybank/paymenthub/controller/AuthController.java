package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.request.LoginRequestDTO;
import com.mybank.paymenthub.dto.response.LoginResponseDTO;
import com.mybank.paymenthub.entity.User;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.repository.UserRepository;
import com.mybank.paymenthub.security.JWTService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final JWTService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid @RequestBody LoginRequestDTO request
    ){

        System.out.println("========== LOGIN API START ==========");

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found",
                                "userName",
                                request.getUsername()
                        )
                );

        System.out.println("USER FOUND = " + user.getUsername());

        System.out.println("DB PASSWORD = " + user.getPassword());
        System.out.println("PASSWORD LENGTH = " + user.getPassword().length());


        boolean match = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        System.out.println("PASSWORD MATCH = " + match);


        if(!match){
            throw new RuntimeException("Invalid Password");
        }


        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );


        System.out.println("TOKEN GENERATED");


        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getRole()
        );
    }
}

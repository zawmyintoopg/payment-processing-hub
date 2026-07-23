package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.UserRequestDTO;
import com.mybank.paymenthub.dto.response.UserResponseDTO;
import com.mybank.paymenthub.entity.User;
import com.mybank.paymenthub.repository.UserRepository;
import com.mybank.paymenthub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO request) {
        User user = new User();

        user.setUsername(
                request.getUsername()
        );
        // Important

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )

        );

        user.setRole(
                request.getRole()
        );

         User savedUser   = userRepository.save(user);

         return new UserResponseDTO(
                 savedUser.getId(),
                 savedUser.getUsername(),
                 savedUser.getRole(),
                 savedUser.getStatus()
         );

       }

    }


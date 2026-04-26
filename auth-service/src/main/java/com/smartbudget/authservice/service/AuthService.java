package com.smartbudget.authservice.service;

import com.smartbudget.authservice.dto.RegisterRequest;
import com.smartbudget.authservice.dto.UserDto;
import com.smartbudget.authservice.entity.User;
import com.smartbudget.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(RegisterRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return mapToDto(userRepository.save(user));
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());

        return dto;
    }
}

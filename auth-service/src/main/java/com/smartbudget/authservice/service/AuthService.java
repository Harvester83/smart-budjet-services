package com.smartbudget.authservice.service;

import com.smartbudget.authservice.dto.AuthResponse;
import com.smartbudget.authservice.dto.LoginRequest;
import com.smartbudget.authservice.dto.RegisterRequest;
import com.smartbudget.authservice.dto.UserDto;
import com.smartbudget.authservice.entity.User;
import com.smartbudget.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserDto register(RegisterRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return mapToDto(userRepository.save(user));
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid email or password"
                ));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());

        return dto;
    }
}

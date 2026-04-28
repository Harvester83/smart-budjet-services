package com.smartbudget.authservice.controller;

import com.smartbudget.authservice.common.dto.ApiResponse;
import com.smartbudget.authservice.dto.AuthResponse;
import com.smartbudget.authservice.dto.LoginRequest;
import com.smartbudget.authservice.dto.RegisterRequest;
import com.smartbudget.authservice.dto.UserDto;
import com.smartbudget.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        AuthResponse authResponse =  authService.login(request);
        return ApiResponse.success(authResponse);
    }
}

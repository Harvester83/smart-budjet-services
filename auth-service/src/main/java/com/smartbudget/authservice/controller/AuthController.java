package com.smartbudget.authservice.controller;

import com.smartbudget.authservice.dto.RegisterRequest;
import com.smartbudget.authservice.dto.UserDto;
import com.smartbudget.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }


}

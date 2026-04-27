package com.smartbudget.authservice.controller;

import com.smartbudget.authservice.dto.UserDto;
import com.smartbudget.authservice.entity.User;
import com.smartbudget.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto create(@RequestBody User user) {
        return userService.create(user);
    }

}

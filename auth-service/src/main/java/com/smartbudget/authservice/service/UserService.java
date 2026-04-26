package com.smartbudget.authservice.service;

import com.smartbudget.authservice.dto.UserDto;
import com.smartbudget.authservice.entity.User;
import com.smartbudget.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto create(User user) {
        User saved = userRepository.save(user);

        return mapToDto(saved);
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}

package com.smartbudget.userservice.controller;

import com.smartbudget.userservice.common.dto.ApiResponse;
import com.smartbudget.userservice.dto.UserProfileResponse;
import com.smartbudget.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getMe(
            @RequestHeader("X-User-Id") Long authUserId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(userProfileService.getProfile(authUserId))
        );
    }
}

package com.smartbudget.userservice.service;


import com.smartbudget.userservice.dto.UserProfileResponse;
import com.smartbudget.userservice.entity.UserProfile;
import com.smartbudget.userservice.repository.UserProfileRepository;
import com.smartbudget.userservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfileResponse getProfile(Long authUserId) {
        UserProfile profile = userProfileRepository.findByAuthUserId(authUserId)
                .orElseThrow(() -> new NotFoundException("User profile not found"));

        return new UserProfileResponse(
                profile.getId(),
                profile.getAuthUserId(),
                profile.getUsername(),
                profile.getEmail(),
                profile.getBalance()
        );
    }
}
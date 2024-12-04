package com.nkonzo.student_portal_api.controller;

import com.nkonzo.student_portal_api.common.request.UpdateProfileRequestDto;
import com.nkonzo.student_portal_api.common.response.ProfileResponseDto;
import com.nkonzo.student_portal_api.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/student")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getStudent(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(profileService.getStudent(token));
    }


    @PutMapping("/profile/update")
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestHeader("Authorization") String token, @RequestBody UpdateProfileRequestDto dto) {
        return ResponseEntity.ok(profileService.updateStudentProfile(dto, token));
    }

}

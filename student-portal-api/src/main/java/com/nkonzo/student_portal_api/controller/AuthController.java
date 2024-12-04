package com.nkonzo.student_portal_api.controller;

import com.nkonzo.student_portal_api.common.request.LoginStudentRequestDto;
import com.nkonzo.student_portal_api.common.request.RegisterStudentDto;
import com.nkonzo.student_portal_api.common.request.VerifyStudentDto;
import com.nkonzo.student_portal_api.common.response.LoginStudentResponseDto;
import com.nkonzo.student_portal_api.common.response.RegisterStudentResponseDto;
import com.nkonzo.student_portal_api.entity.Student;
import com.nkonzo.student_portal_api.service.AuthenticationService;
import com.nkonzo.student_portal_api.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterStudentResponseDto> register(@Valid @RequestBody RegisterStudentDto dto) {
        RegisterStudentResponseDto registeredStudent = authService.register(dto);
        return ResponseEntity.ok(registeredStudent);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@Valid @RequestBody VerifyStudentDto dto) {
        authService.verifyEmail(dto.getEmail(), dto.getVerificationCode());
        return ResponseEntity.ok("Account was verified");
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendVerificationCode(@RequestParam String email) {
        authService.resendVerificationCode(email);
        return ResponseEntity.ok("Verification code sent");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginStudentResponseDto> login(@Valid @RequestBody LoginStudentRequestDto dto) {
        Student student = authService.authenticateStudent(dto);
        String jwtToken = jwtService.generateToken(student);
        LoginStudentResponseDto loginStudentResponseDto = LoginStudentResponseDto.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginStudentResponseDto);
    }




}

package com.nkonzo.student_portal_api.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginStudentResponseDto {
    private String token;
    private long expiresIn;
}

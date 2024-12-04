package com.nkonzo.student_portal_api.common.request;

import lombok.Data;

@Data
public class VerifyStudentDto {
    private String email;
    private String verificationCode;
}
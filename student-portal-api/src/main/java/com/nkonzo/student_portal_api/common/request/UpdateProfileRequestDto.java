package com.nkonzo.student_portal_api.common.request;

import lombok.Data;

@Data
public class UpdateProfileRequestDto {
    private String email;
    private String password;
    private String phoneNumber;
}

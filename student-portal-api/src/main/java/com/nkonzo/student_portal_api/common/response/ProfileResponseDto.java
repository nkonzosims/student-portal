package com.nkonzo.student_portal_api.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponseDto {

    private String studentNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
}

package com.nkonzo.student_portal_api.common.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginStudentRequestDto {

    @Email(message = "Enter a valid email!")
    private String email;

    @Size(min = 6, message = "Password length must be more than 6 character!")
    private String password;
}

package com.nkonzo.student_portal_api.common.request;

import com.nkonzo.student_portal_api.common.validation.passport.PassportNumberOrID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class RegisterStudentDto {

    @NotNull(message = "First name cannot be null!")
    String firstName;

    @NotNull(message = "Last name cannot be null!")
    String lastName;

    @NotNull(message = "Date of birth cannot be null!")
    String dateOfBirth;

    @Email(message = "Enter a valid email!")
    @NotNull(message = "Email cannot be null!")
    String email;

    @NotNull(message = "Password cannot be null!")
    @Size(min = 6, message = "Password length must be more than 6 characters!")
    String password;

    @NotNull(message = "Phone number cannot be null!")
    @Size(max = 12, message = "Phone number length must be less than 12 characters!")
    @Pattern(regexp = "^(\\+27|0)[6-8][0-9]{8}$", message = "Enter a valid phone number! e.g 0812345678")
    String phoneNumber;

    @PassportNumberOrID
    @Size(min = 13, max = 13, message = "ID number length must be 13 characters!")
    String idNumber;

    @PassportNumberOrID
    @Size(min = 6, max = 11, message = "Passport number length must be less than 12 characters!")
    String passportNumber;

}


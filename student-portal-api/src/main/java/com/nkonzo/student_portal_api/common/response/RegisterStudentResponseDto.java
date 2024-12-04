package com.nkonzo.student_portal_api.common.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Builder
@Getter
@Setter
public class RegisterStudentResponseDto {

        private UUID id;
        private String studentNumber;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String idNumber;
        private String passportNumber;
        private String dateOfBirth;
        private Date createdAt;
        private Date updatedAt;
}

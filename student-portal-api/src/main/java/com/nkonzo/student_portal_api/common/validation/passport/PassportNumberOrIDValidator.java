package com.nkonzo.student_portal_api.common.validation.passport;

import com.nkonzo.student_portal_api.common.request.RegisterStudentDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PassportNumberOrIDValidator implements ConstraintValidator<PassportNumberOrID, RegisterStudentDto> {

    @Override
    public boolean isValid(RegisterStudentDto dto, ConstraintValidatorContext context) {
        return dto.getIdNumber() != null || dto.getPassportNumber() != null;
    }
}
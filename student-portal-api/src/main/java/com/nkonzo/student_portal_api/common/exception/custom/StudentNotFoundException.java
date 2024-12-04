package com.nkonzo.student_portal_api.common.exception.custom;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

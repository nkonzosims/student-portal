package com.nkonzo.student_portal_api.common.exception.custom;

public class StudentNotVerifiedException extends RuntimeException {
    public StudentNotVerifiedException(String message) {
        super(message);
    }
}
package com.nkonzo.student_portal_api.common.exception.custom;

public class StudentAlreadyExistException extends RuntimeException {
    public StudentAlreadyExistException(String message) {
        super(message);
    }
}
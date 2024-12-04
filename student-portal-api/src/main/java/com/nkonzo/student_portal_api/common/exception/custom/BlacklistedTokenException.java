package com.nkonzo.student_portal_api.common.exception.custom;

public class BlacklistedTokenException extends RuntimeException {
    public BlacklistedTokenException(String message) {
        super(message);
    }
}

package com.backend.ecommerce.exception;

import org.springframework.http.ProblemDetail;

public class PasswordDidNotMatchException extends RuntimeException {
    public PasswordDidNotMatchException(String message) {
        super(message);
    }
}

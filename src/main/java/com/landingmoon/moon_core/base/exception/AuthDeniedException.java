package com.landingmoon.moon_core.base.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthDeniedException extends AuthenticationException {

    public AuthDeniedException(String msg) {
        super(msg);
    }
}

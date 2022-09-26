package com.landingmoon.moon_core.base.exception;

import org.springframework.security.core.AuthenticationException;

public class BadMethodException extends AuthenticationException {

    public BadMethodException(String msg) {
        super(msg);
    }

}

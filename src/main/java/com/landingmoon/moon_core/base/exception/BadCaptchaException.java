package com.landingmoon.moon_core.base.exception;

import org.springframework.security.core.AuthenticationException;

public class BadCaptchaException extends AuthenticationException {
    public BadCaptchaException(String msg) {
        super(msg);
    }
}

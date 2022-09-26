package com.landingmoon.moon_core.base.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaIsNullException extends AuthenticationException {
    public CaptchaIsNullException(String msg) {
        super(msg);
    }
}

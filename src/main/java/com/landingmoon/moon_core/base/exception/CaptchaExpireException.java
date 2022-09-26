package com.landingmoon.moon_core.base.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaExpireException extends AuthenticationException {
    public CaptchaExpireException(String msg) {
        super(msg);
    }
}

package com.xli.sso.security.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String msg) {
        super(msg);
    }
}

package jhhong.example.user.global.security.jwt.exception;

import jhhong.example.user.global.error.ErrorCode;
import jhhong.example.user.global.error.GlobalException;

public class InvalidAuthenticationException extends GlobalException {
    public InvalidAuthenticationException() {
        super(ErrorCode.INVALID_AUTHENTICATION);
    }
}

package jhhong.example.rsocketchatting.global.security.jwt.exception;

import jhhong.example.rsocketchatting.global.error.ErrorCode;
import jhhong.example.rsocketchatting.global.error.GlobalException;

public class InvalidAuthenticationException extends GlobalException {
    public InvalidAuthenticationException() {
        super(ErrorCode.INVALID_AUTHENTICATION);
    }
}

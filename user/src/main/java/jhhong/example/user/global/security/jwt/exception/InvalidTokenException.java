package jhhong.example.user.global.security.jwt.exception;

import jhhong.example.user.global.error.ErrorCode;
import jhhong.example.user.global.error.GlobalException;

public class InvalidTokenException extends GlobalException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}

package jhhong.example.rsocketchatting.global.security.jwt.exception;

import jhhong.example.rsocketchatting.global.error.ErrorCode;
import jhhong.example.rsocketchatting.global.error.GlobalException;

public class InvalidTokenException extends GlobalException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}

package jhhong.example.user.global.validation;

import jhhong.example.user.global.error.ErrorCode;
import jhhong.example.user.global.error.GlobalException;

public class BadRequestException extends GlobalException {
    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}

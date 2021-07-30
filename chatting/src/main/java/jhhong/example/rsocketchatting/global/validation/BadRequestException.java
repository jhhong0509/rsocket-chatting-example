package jhhong.example.rsocketchatting.global.validation;

import jhhong.example.rsocketchatting.global.error.ErrorCode;
import jhhong.example.rsocketchatting.global.error.GlobalException;

public class BadRequestException extends GlobalException {
    public BadRequestException() {
        super(ErrorCode.BAD_REQUEST);
    }
}

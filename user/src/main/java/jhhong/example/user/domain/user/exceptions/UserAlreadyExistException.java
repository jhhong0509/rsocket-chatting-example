package jhhong.example.user.domain.user.exceptions;

import jhhong.example.user.global.error.ErrorCode;
import jhhong.example.user.global.error.GlobalException;

public class UserAlreadyExistException extends GlobalException {
    public UserAlreadyExistException() {
        super(ErrorCode.USER_ALREADY_EXIST);
    }
}

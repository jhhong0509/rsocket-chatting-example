package jhhong.example.user.domain.auth.exceptions;

import jhhong.example.user.global.error.ErrorCode;
import jhhong.example.user.global.error.GlobalException;

public class UserNotFoundException extends GlobalException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}

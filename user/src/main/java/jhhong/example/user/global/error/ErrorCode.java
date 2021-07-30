package jhhong.example.user.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(404, "User Not Found"),
    USER_ALREADY_EXIST(409, "User Already Exist"),
    BAD_REQUEST(400, "Check Json"),
    INVALID_TOKEN(401, "Invalid Token"),
    INVALID_AUTHENTICATION(401, "Invalid Authentication");



    private final int code;
    private final String message;
}

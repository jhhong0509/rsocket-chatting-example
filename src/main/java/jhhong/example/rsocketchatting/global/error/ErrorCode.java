package jhhong.example.rsocketchatting.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(401, "Invalid Token"),
    INVALID_AUTHENTICATION(401, "Invalid Authentication");


    private final int code;
    private final String message;
}

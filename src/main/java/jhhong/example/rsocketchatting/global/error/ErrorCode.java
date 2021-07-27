package jhhong.example.rsocketchatting.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(401, "Invalid Token");


    private final int code;
    private final String message;
}

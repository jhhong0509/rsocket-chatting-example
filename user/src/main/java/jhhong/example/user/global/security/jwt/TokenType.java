package jhhong.example.user.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("access", 7200),
    REFRESH_TOKEN("refresh", 1209600);

    private final String type;

    private final long exp;
}

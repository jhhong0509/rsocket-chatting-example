package jhhong.example.user.domain.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthResponse {

    private final String accessToken;
}

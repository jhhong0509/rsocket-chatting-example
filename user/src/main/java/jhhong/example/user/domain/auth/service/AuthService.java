package jhhong.example.user.domain.auth.service;

import jhhong.example.user.domain.auth.payload.AuthRequest;
import jhhong.example.user.domain.auth.payload.AuthResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthResponse> createToken(AuthRequest request);
}

package jhhong.example.user.domain.auth.handler;

import jhhong.example.user.domain.auth.payload.AuthRequest;
import jhhong.example.user.domain.auth.service.AuthService;
import jhhong.example.user.global.validation.CustomValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthHandler {

    private final AuthService authService;
    private final CustomValidation validation;

    public Mono<ServerResponse> createToken(ServerRequest request) {
        return request.bodyToMono(AuthRequest.class)
                .flatMap(validation::validate)
                .flatMap(authService::createToken)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}

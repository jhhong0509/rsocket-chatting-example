package jhhong.example.user.domain.user.handler;

import jhhong.example.user.domain.user.payload.CreateUserRequest;
import jhhong.example.user.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class UserHandler {

    private final UserService userService;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(CreateUserRequest.class)
                .flatMap(userService::createUserRequest)
                .flatMap(result -> ServerResponse.created(URI.create("/user")).body(result, Void.class));
    }

    public Mono<ServerResponse> getUserInfo(ServerRequest request) {
        return Mono.just(request.pathVariable("userEmail"))
                .flatMap(userService::getUserInfo)
                .flatMap(ServerResponse.ok()::bodyValue);
    }
}

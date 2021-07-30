package jhhong.example.user.domain.auth.router;

import jhhong.example.user.domain.auth.handler.AuthHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class AuthRouter {

    private final AuthHandler authHandler;

    @Bean
    public RouterFunction<ServerResponse> authRouters() {
        return route()
                .GET("/auth", accept(MediaType.APPLICATION_JSON), authHandler::createToken)
                .build();
    }
}

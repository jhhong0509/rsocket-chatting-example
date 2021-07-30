package jhhong.example.user.domain.user.router;

import jhhong.example.user.domain.user.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@RequiredArgsConstructor
@Configuration
public class UserRouter {

    private final UserHandler userHandler;

    @Bean
    public RouterFunction<ServerResponse> userRouters() {
        return route().path("/user", builder -> builder.nest(accept(APPLICATION_JSON), route -> route
                .POST("", userHandler::createUser)
                .GET("/{userEmail}", userHandler::getUserInfo)))
                .build();
    }
}

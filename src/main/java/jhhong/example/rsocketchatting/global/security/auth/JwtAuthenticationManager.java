package jhhong.example.rsocketchatting.global.security.auth;

import jhhong.example.rsocketchatting.domain.user.entity.UserRepository;
import jhhong.example.rsocketchatting.global.security.jwt.exception.InvalidAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findById)
                .switchIfEmpty(Mono.error(InvalidAuthenticationException::new))
                .flatMap(user -> Mono.just(authentication));
    }
}

package jhhong.example.user.global.security.auth;

import jhhong.example.user.domain.user.entity.UserRepository;
import jhhong.example.user.global.security.jwt.exception.InvalidAuthenticationException;
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
    public Mono<Authentication> authenticate(Authentication authentication) {   // 해당 인증 객체가 실제로 유효한지 판단
        return Mono.just(authentication)
                .map(Authentication::getName)
                .flatMap(userRepository::findById)
                .switchIfEmpty(Mono.error(InvalidAuthenticationException::new))
                .flatMap(user -> Mono.just(authentication));
    }
}

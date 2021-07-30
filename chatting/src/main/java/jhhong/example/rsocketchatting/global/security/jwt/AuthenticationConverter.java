package jhhong.example.rsocketchatting.global.security.jwt;

import jhhong.example.rsocketchatting.global.security.auth.AuthenticationToken;
import jhhong.example.rsocketchatting.global.security.jwt.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class AuthenticationConverter implements ServerAuthenticationConverter {     // 요청을 Authentication 객체로 변환하는 역할

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(this::extractToken)
                .flatMap(this::getAuthentication);
    }

    private Mono<String> extractToken(ServerWebExchange exchange) {     // 토큰 부분을 추출
        return Mono.justOrEmpty(exchange.getRequest().getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION))
                .map(s -> s.substring(7));
    }

    private Mono<AuthenticationToken> getAuthentication(String token) {     // token 으로 Authentication 객체 생성
        return jwtTokenProvider.parseToken(token)
                .filter(claims -> claims.get("type")
                        .equals(TokenType.ACCESS_TOKEN.getType()))
                .flatMap(claims -> Mono.just(new AuthenticationToken(claims.getSubject(), token)))
                .switchIfEmpty(Mono.error(InvalidTokenException::new));
    }

}

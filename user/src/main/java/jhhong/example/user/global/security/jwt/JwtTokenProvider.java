package jhhong.example.user.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jhhong.example.user.global.security.jwt.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String id, TokenType type) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .setExpiration(new Date(System.currentTimeMillis() + type.getExp() * 1000))
                .setIssuedAt(new Date())
                .claim("type", type.getType())
                .compact();
    }

    public Mono<Claims> parseToken(String token) {
        try {
            return Mono.just(Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
                    .getBody());
        } catch (Exception e) {
            return Mono.error(InvalidTokenException::new);
        }
    }


}

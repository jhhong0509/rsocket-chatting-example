package jhhong.example.user.global.security.config;

import jhhong.example.user.global.security.auth.JwtAuthenticationManager;
import jhhong.example.user.global.security.jwt.AuthenticationConverter;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AuthenticationWebFilter {
    public AuthenticationFilter(JwtAuthenticationManager authenticationManager,
                                AuthenticationConverter converter) {
        super(authenticationManager);
        setServerAuthenticationConverter(converter);
    }
}

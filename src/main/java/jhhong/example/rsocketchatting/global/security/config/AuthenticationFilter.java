package jhhong.example.rsocketchatting.global.security.config;

import jhhong.example.rsocketchatting.global.security.auth.JwtAuthenticationManager;
import jhhong.example.rsocketchatting.global.security.jwt.AuthenticationConverter;
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

package jhhong.example.rsocketchatting.global.security.config;

import jhhong.example.rsocketchatting.global.adapter.outbound.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;

@RequiredArgsConstructor
@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final UserAdapter userAdapter;
    private final JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager;

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {      // Jwt를 인증하고 권한을 결정한다.
        return security.authorizePayload(authorize ->
                        authorize
                                .setup().permitAll()
                                .anyExchange().authenticated())
                .jwt(jwt -> {
                    try {
                        jwt.authenticationManager(jwtReactiveAuthenticationManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).build();
    }

    @Bean
    public ReactiveUserDetailsService reactiveUserDetailsService() {
        return username -> userAdapter.getUserInfo(username)
                .map(AuthDetails::new);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

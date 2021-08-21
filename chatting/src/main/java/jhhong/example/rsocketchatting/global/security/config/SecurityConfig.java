package jhhong.example.rsocketchatting.global.security.config;

import jhhong.example.rsocketchatting.global.adapter.outbound.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.rsocket.authentication.BearerPayloadExchangeConverter;
import org.springframework.security.rsocket.authentication.PayloadExchangeAuthenticationConverter;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import reactor.core.Disposables;

import javax.crypto.spec.SecretKeySpec;

@RequiredArgsConstructor
@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private final UserAdapter userAdapter;

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public PayloadExchangeAuthenticationConverter authenticationConverter() {
        return new BearerPayloadExchangeConverter();
    }

    public ReactiveJwtDecoder reactiveJwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), MacAlgorithm.HS256.getName());

        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager() {
        return new JwtReactiveAuthenticationManager(reactiveJwtDecoder());
    }

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {      // Jwt를 인증하고 권한을 결정한다.
        return security.authorizePayload(authorize ->
                        authorize
                                .setup().permitAll()
                                .anyExchange().authenticated())
                .jwt(jwtSpec -> jwtSpec.authenticationManager(jwtReactiveAuthenticationManager()))
                .build();
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

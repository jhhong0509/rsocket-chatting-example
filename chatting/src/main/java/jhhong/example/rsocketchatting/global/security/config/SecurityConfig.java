package jhhong.example.rsocketchatting.global.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtReactiveAuthenticationManager;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;

import javax.crypto.spec.SecretKeySpec;

@RequiredArgsConstructor
@Configuration
@EnableRSocketSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean // (4)
    public RSocketMessageHandler messageHandler(RSocketStrategies strategies) {

        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.getArgumentResolverConfigurer().addCustomResolver(new AuthenticationPrincipalArgumentResolver());
        handler.setRSocketStrategies(strategies);
        return handler;
    }

    @Bean
    PayloadSocketAcceptorInterceptor authorization(RSocketSecurity security) {      // Jwt를 인증하고 권한을 결정한다.
        return security.authorizePayload(authorize ->
                        authorize
                                .setup().permitAll()
                                .anyExchange().authenticated())
                .jwt(jwt -> {
                    try {
                        jwt.authenticationManager(jwtReactiveAuthenticationManager(reactiveJwtDecoder()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).build();
    }

    @Bean
    public JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager(ReactiveJwtDecoder reactiveJwtDecoder) {
        JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager = new JwtReactiveAuthenticationManager(reactiveJwtDecoder);

        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");                     // 권한들이 ROLE_ prefix를 갖도록 함
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        jwtReactiveAuthenticationManager.setJwtAuthenticationConverter(new ReactiveJwtAuthenticationConverterAdapter(authenticationConverter));
        return jwtReactiveAuthenticationManager;
    }

    @Bean
    RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                .decoders(decoders -> decoders.add(new Jackson2JsonDecoder()))
                .decoders(decoders -> decoders.add(new Jackson2CborDecoder()))
                .encoders(encoders -> encoders.add(new Jackson2CborEncoder()))
                .encoders(encoders -> encoders.add(new Jackson2JsonEncoder()))
                .encoders(encoders -> encoders.add(new SimpleAuthenticationEncoder()))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), MacAlgorithm.HS256.getName());

        return NimbusReactiveJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

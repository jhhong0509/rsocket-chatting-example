package jhhong.example.user.domain.auth.service;

import jhhong.example.user.domain.auth.payload.AuthRequest;
import jhhong.example.user.domain.auth.payload.AuthResponse;
import jhhong.example.user.domain.user.entity.UserRepository;
import jhhong.example.user.domain.auth.exceptions.UserNotFoundException;
import jhhong.example.user.global.security.jwt.JwtTokenProvider;
import jhhong.example.user.global.security.jwt.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder encoder;

    @Override
    public Mono<AuthResponse> createToken(AuthRequest request) {
        return userRepository.findById(request.getEmail())
                .filter(user -> encoder.matches(request.getPassword(), user.getPassword()))
                .map(user -> jwtTokenProvider.generateToken(user.getEmail(), TokenType.ACCESS_TOKEN))
                .map(AuthResponse::new)
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }
}

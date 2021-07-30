package jhhong.example.user.domain.user.service;

import jhhong.example.user.domain.user.entity.User;
import jhhong.example.user.domain.user.entity.UserRepository;
import jhhong.example.user.domain.user.exceptions.UserAlreadyExistException;
import jhhong.example.user.domain.user.payload.CreateUserRequest;
import jhhong.example.user.domain.user.payload.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserInfoResponse> getUserInfo(String userId) {
        return userRepository.findById(userId)
                .map(user -> UserInfoResponse.builder()
                        .email(user.getEmail())
                        .nickName(user.getNickname())
                        .build());
    }

    @Override
    public Mono<Void> createUserRequest(CreateUserRequest request) {
        return userRepository.existsByEmail(request.getEmail())
                .filter(bool -> !bool)
                .map(bool -> User.builder()
                        .password(passwordEncoder.encode(request.getPassword()))
                        .nickname(request.getNickName())
                        .email(request.getEmail())
                        .build())
                .flatMap(userRepository::save)
                .switchIfEmpty(Mono.error(UserAlreadyExistException::new))
                .then();
    }

}

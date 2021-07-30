package jhhong.example.user.domain.user.service;

import jhhong.example.user.domain.user.payload.CreateUserRequest;
import jhhong.example.user.domain.user.payload.UserInfoResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserInfoResponse> getUserInfo(String userId);
    Mono<Void> createUserRequest(CreateUserRequest request);
}

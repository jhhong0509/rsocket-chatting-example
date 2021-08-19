package jhhong.example.rsocketchatting.global.adapter.outbound;

import jhhong.example.rsocketchatting.global.adapter.payload.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public interface UserAdapter {
    Mono<UserInfoResponse> getUserInfo(String userEmail);
}

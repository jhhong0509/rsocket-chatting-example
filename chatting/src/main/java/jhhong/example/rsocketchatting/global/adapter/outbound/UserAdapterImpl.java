package jhhong.example.rsocketchatting.global.adapter.outbound;

import jhhong.example.rsocketchatting.global.adapter.payload.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class UserAdapterImpl implements UserAdapter {

    private final WebClient webClient;
    private static final String GET_USER_URI = "localhost:8080/user/{userEmail}";

    @Override
    public Mono<UserInfoResponse> getUserInfo(String userEmail) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(GET_USER_URI)
                        .build(userEmail))
                .retrieve()
                .bodyToMono(UserInfoResponse.class);
    }
}

package jhhong.example.rsocketchatting.domain.chat.service;

import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

public interface ChatService {

    Mono<Void> joinRoom(String roomId);

    Mono<Void> sendMessage(String message, String roomId);

}

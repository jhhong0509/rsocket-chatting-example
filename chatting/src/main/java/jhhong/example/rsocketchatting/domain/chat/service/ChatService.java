package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {

    Mono<Void> joinRoom(String roomId);

    Mono<Void> sendMessage(String message, String roomId);

    Flux<ChatResponse> getMessage(String roomId);

}

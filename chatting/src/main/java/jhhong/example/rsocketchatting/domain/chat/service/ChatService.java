package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {


    Mono<Void> sendMessage(String message, String roomId);

    Flux<ChatResponse> getMessage(String roomId);

}

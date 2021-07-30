package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<Void> sendMessage(String message);

    Flux<Chat> getMessage();
}

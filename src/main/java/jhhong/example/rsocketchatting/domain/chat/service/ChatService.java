package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.entity.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<Void> sendMessage(String chatRoomId, String message);

    Flux<Message> getMessage(String chatRoomId);
}

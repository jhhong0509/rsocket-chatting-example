package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatRequest;
import reactor.core.publisher.Mono;

public interface ChatService {

    Mono<Void> sendMessage(ChatRequest request, String roomId);

}

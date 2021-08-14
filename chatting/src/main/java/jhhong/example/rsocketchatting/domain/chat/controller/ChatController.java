package jhhong.example.rsocketchatting.domain.chat.controller;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@MessageMapping("api.v1.messages")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("stream")
    public Mono<Void> sendMessage(@Payload String message) {

    }

    @MessageMapping("stream")
    public Flux<Chat> getMessage() {
        return chatService.getMessage();
    }

}
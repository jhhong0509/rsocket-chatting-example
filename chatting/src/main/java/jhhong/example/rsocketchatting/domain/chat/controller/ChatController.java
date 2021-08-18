package jhhong.example.rsocketchatting.domain.chat.controller;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatRequest;
import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import jhhong.example.rsocketchatting.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("{roomId}.send")
    public Mono<Void> sendMessage(@Payload ChatRequest message, @DestinationVariable String roomId) {
        return chatService.sendMessage(message, roomId);
    }

    @MessageMapping("{roomId}.stream")
    public Flux<ChatResponse> getMessage(@DestinationVariable String roomId) {
        return chatService.getMessage(roomId);
    }

}
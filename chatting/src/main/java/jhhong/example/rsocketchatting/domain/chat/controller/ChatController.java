package jhhong.example.rsocketchatting.domain.chat.controller;

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
@MessageMapping("api.v1.messages")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("join.chatroom.{roomId}")
    public Mono<Void> joinRoom(@DestinationVariable String roomId) {
        return chatService.joinRoom(roomId);
    }

    @MessageMapping("{roomId}.send")
    public Mono<Void> sendMessage(@Payload String message, @DestinationVariable String roomId) {
        return chatService.sendMessage(message, roomId);
    }

    @MessageMapping("{roomId}.stream")
    public Flux<ChatResponse> getMessage(@DestinationVariable String roomId) {
        return chatService.getMessage(roomId);
    }

}
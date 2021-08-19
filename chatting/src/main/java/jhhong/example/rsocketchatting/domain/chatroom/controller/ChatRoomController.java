package jhhong.example.rsocketchatting.domain.chatroom.controller;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import jhhong.example.rsocketchatting.domain.chatroom.payload.ChatRoomResponse;
import jhhong.example.rsocketchatting.domain.chatroom.payload.CreateRoomRequest;
import jhhong.example.rsocketchatting.domain.chatroom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @MessageMapping("join.chatroom.{roomId}")
    public Flux<ChatResponse> joinRoom(@DestinationVariable String roomId) {
        return chatRoomService.joinRoom(roomId);
    }

    @MessageMapping("create.chatroom")
    public Mono<Void> createRoom(@Payload CreateRoomRequest request) {
        return chatRoomService.createRoom(request);
    }

    @MessageMapping("chatroom")
    public Flux<ChatRoomResponse> getChatRoom() {
        return chatRoomService.getChatRoom();
    }

}

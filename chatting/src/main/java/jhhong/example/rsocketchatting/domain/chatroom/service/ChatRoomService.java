package jhhong.example.rsocketchatting.domain.chatroom.service;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import jhhong.example.rsocketchatting.domain.chatroom.payload.ChatRoomResponse;
import jhhong.example.rsocketchatting.domain.chatroom.payload.CreateRoomRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRoomService {
    Flux<ChatResponse> joinRoom(String roomId);
    Mono<Void> createRoom(CreateRoomRequest request);
    Flux<ChatRoomResponse> getChatRoom();
}

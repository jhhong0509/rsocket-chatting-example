package jhhong.example.rsocketchatting.domain.chatroom.service;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import jhhong.example.rsocketchatting.domain.chatroom.entity.ChatRoom;
import jhhong.example.rsocketchatting.domain.chatroom.entity.ChatRoomRepository;
import jhhong.example.rsocketchatting.domain.chatroom.payload.ChatRoomResponse;
import jhhong.example.rsocketchatting.domain.chatroom.payload.CreateRoomRequest;
import jhhong.example.rsocketchatting.global.adapter.outbound.UserAdapter;
import jhhong.example.rsocketchatting.global.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final Sender sender;
    private final Receiver receiver;
    private final ChatRoomRepository chatRoomRepository;
    private final UserAdapter userAdapter;

    @Override
    public Flux<ChatResponse> joinRoom(String roomId, String email) {
        return join(roomId, email)
                .thenMany(getMessageFromQueue(roomId, email));
    }

    @Override
    public Mono<Void> createRoom(CreateRoomRequest request, String email) {
        return chatRoomRepository.save(ChatRoom.builder()
                        .members(List.of("test"))
                        .roomName(request.getRoomName())
                        .build())
                .flatMap(chatRoom -> sender.declareExchange(ExchangeSpecification
                        .exchange(RabbitMQConfig.EXCHANGE_NAME)
                        .type("direct")).then(Mono.just(chatRoom)))
                .flatMap(chatRoom -> sender.declareExchange(ExchangeSpecification
                                .exchange()
                                .name(chatRoom.getId())
                                .type("fanout"))
                        .then(Mono.just(chatRoom)))
                .flatMap(chatRoom -> sender.bindExchange(BindingSpecification
                                .binding()
                                .routingKey(chatRoom.getId())
                                .exchangeTo(chatRoom.getId())
                                .exchangeFrom(RabbitMQConfig.EXCHANGE_NAME))
                        .then(Mono.just(chatRoom)))
                .flatMap(chatRoom -> join(chatRoom.getId(), email))
                .then();
    }

    @Override
    public Flux<ChatRoomResponse> getChatRoom() {
        return null;
    }

    private Mono<Void> join(String roomId, String email) {
        return sender
                .declareQueue(QueueSpecification.queue(roomId + "." + email)
                        .autoDelete(false))
                .flatMap(declareOk -> sender.bindQueue(BindingSpecification.binding()
                        .routingKey(roomId)
                        .queue(roomId + "." + email)
                        .exchangeFrom(roomId)))
                .then();
    }

    private Flux<ChatResponse> getMessageFromQueue(String roomId, String email) {
        return receiver.consumeAutoAck(roomId + "." + email)
                .map(delivery -> new ChatResponse(new String(delivery.getBody())));
    }

}
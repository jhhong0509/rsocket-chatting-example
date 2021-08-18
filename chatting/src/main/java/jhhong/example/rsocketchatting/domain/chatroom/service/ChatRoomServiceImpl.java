package jhhong.example.rsocketchatting.domain.chatroom.service;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import jhhong.example.rsocketchatting.domain.chatroom.entity.ChatRoom;
import jhhong.example.rsocketchatting.domain.chatroom.entity.ChatRoomRepository;
import jhhong.example.rsocketchatting.domain.chatroom.payload.ChatRoomResponse;
import jhhong.example.rsocketchatting.domain.chatroom.payload.CreateRoomRequest;
import jhhong.example.rsocketchatting.global.adapter.outbound.UserAdapter;
import jhhong.example.rsocketchatting.global.jackson.ReactiveObjectMapper;
import jhhong.example.rsocketchatting.global.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private static final String JOIN_MESSAGE = "새로운 회원이 입장했습니다";

    private final Sender sender;
    private final Receiver receiver;
    private final ChatRoomRepository chatRoomRepository;
    private final UserAdapter userAdapter;

    @Override
    public Flux<ChatResponse> joinRoom(String roomId) {
        return receiver.consumeAutoAck(roomId)
                .doOnNext(delivery -> System.out.println(new String(delivery.getBody())))
                .map(delivery -> new ChatResponse(new String(delivery.getBody())));
    }

    @Override
    public Mono<Void> createRoom(CreateRoomRequest request) {
        Mono<ChatRoom> chatRoomMono = chatRoomRepository.save(ChatRoom.builder()
                .members(List.of("test"))
                .roomName(request.getRoomName())
                .build());
        return chatRoomMono.flatMap(chatRoom -> sender.declareQueue(QueueSpecification.queue(chatRoom.getId())
                        .durable(true)
                        .exclusive(false)
                        .autoDelete(false)))
                .flatMap(declareOk -> sender.declareExchange(ExchangeSpecification
                        .exchange(RabbitMQConfig.EXCHANGE_NAME)
                        .type("direct")))
                .flatMap(declareOk -> chatRoomMono)
                .flatMap(chatRoom -> sender.bindQueue(BindingSpecification
                        .binding()
                        .routingKey(chatRoom.getId())
                        .queue(chatRoom.getId())
                        .exchangeFrom(RabbitMQConfig.EXCHANGE_NAME)))
                .then();
    }

    @Override
    public Flux<ChatRoomResponse> getChatRoom() {
        return null;
    }

}
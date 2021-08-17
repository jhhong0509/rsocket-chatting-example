package jhhong.example.rsocketchatting.domain.chatroom.service;

import jhhong.example.rsocketchatting.domain.chatroom.entity.ChatRoom;
import jhhong.example.rsocketchatting.domain.chatroom.entity.ChatRoomRepository;
import jhhong.example.rsocketchatting.domain.chatroom.payload.CreateRoomRequest;
import jhhong.example.rsocketchatting.global.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private static final String JOIN_MESSAGE = "새로운 회원이 입장했습니다";

    private final Sender sender;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Mono<Void> joinRoom(String roomId) {
        return sender.bindQueue(BindingSpecification
                        .queueBinding(roomId, "test.chat." + roomId, "test.chat." + roomId))
                .map(bindOk -> Mono.just(new OutboundMessage(RabbitMQConfig.EXCHANGE_NAME, roomId, JOIN_MESSAGE.getBytes())))
                .flatMap(sender::send)
                .then();
    }

    @Override
    public Mono<Void> createRoom(CreateRoomRequest request) {
        Mono<ChatRoom> chatRoomMono =  chatRoomRepository.save(ChatRoom.builder()
                        .roomName(request.getRoomName())
                        .build());
        return chatRoomMono.flatMap(chatRoom -> sender.declareQueue(QueueSpecification.queue("test.chat." + chatRoom.getId())
                        .durable(true)
                        .exclusive(false)
                        .autoDelete(false)))
                .flatMap(declareOk -> chatRoomMono)
                .flatMap(chatRoom -> sender.bindExchange(BindingSpecification.queueBinding(RabbitMQConfig.EXCHANGE_NAME,
                                chatRoom.getId(), "test.chat." + chatRoom.getId())))
                .then();
    }

}
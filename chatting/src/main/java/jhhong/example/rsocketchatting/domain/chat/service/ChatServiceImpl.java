package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.entity.ChatRepository;
import jhhong.example.rsocketchatting.domain.chat.payload.ChatRequest;
import jhhong.example.rsocketchatting.global.jackson.ReactiveObjectMapper;
import jhhong.example.rsocketchatting.global.rabbitmq.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

@RequiredArgsConstructor
@Component
public class ChatServiceImpl implements ChatService {

    private final Receiver receiver;
    private final Sender sender;
    private final ReactiveObjectMapper objectMapper;
    private final ChatRepository chatRepository;

    @Override
    public Mono<Void> sendMessage(ChatRequest request, String roomId) {
        return objectMapper.encodeValue(ChatRequest.class, Mono.just(request))
                .map(buffer -> Mono.just(new OutboundMessage(RabbitMQConfig.EXCHANGE_NAME, roomId, buffer.asByteBuffer().array())))
                .flatMap(sender::send)
                .flatMap(unused -> chatRepository.save(buildChat(request.getContent(), roomId)))
                .then();
    }

    private Chat buildChat(String message, String chatRoomId) {
        return Chat.builder()
                .chatRoomId(chatRoomId)
                .message(message)
                .senderEmail("testEmail")
                .senderName("name")
                .build();
    }
}

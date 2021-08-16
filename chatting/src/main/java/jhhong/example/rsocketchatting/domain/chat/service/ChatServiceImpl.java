package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.entity.ChatRepository;
import jhhong.example.rsocketchatting.domain.chat.payload.ChatResponse;
import jhhong.example.rsocketchatting.global.objectmapper.ReactiveObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class ChatServiceImpl implements ChatService {

    private final Receiver receiver;
    private final Sender sender;
    private final ReactiveObjectMapper objectMapper;
    private final ChatRepository chatRepository;

    @Override
    public Mono<Void> sendMessage(String message, String roomId) {
        return objectMapper.encodeValue(String.class, Mono.just(message))
                .map(buffer -> Mono.just(new OutboundMessage("", "", buffer.asByteBuffer().array())))
                .flatMap(sender::send)
                .flatMap(unused -> chatRepository.save(buildChat(message, roomId)))
                .then();
    }

    @Override
    public Flux<ChatResponse> getMessage(String roomId) {
        return receiver.consumeAutoAck("test.chat." + roomId)
                .doOnNext(System.out::println)
                .map(delivery -> new ChatResponse(Arrays.toString(delivery.getBody())));
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

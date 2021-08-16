package jhhong.example.rsocketchatting.domain.chat.service;

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

    private static final String JOIN_MESSAGE = "새로운 회원이 입장했습니다";

    private final Receiver receiver;
    private final Sender sender;
    private final ReactiveObjectMapper objectMapper;

    @Override
    public Mono<Void> joinRoom(String roomId) {
        return sender.declareQueue(QueueSpecification.queue("test.chat." + roomId)
                        .durable(true)
                        .exclusive(false)
                        .autoDelete(false))
                .map(declareOk -> Mono.just(new OutboundMessage("", "test.chat." + roomId, JOIN_MESSAGE.getBytes())))
                .flatMap(sender::send)
                .then();
    }

    @Override
    public Mono<Void> sendMessage(String message, String roomId) {
        return objectMapper.encodeValue(String.class, Mono.just(message))
                .map(buffer -> Mono.just(new OutboundMessage("", "", buffer.asByteBuffer().array())))
                .flatMap(sender::send)
                .then();
    }

    @Override
    public Flux<ChatResponse> getMessage(String roomId) {
        return receiver.consumeAutoAck("test.chat." + roomId)
                .doOnNext(System.out::println)
                .map(delivery -> new ChatResponse(Arrays.toString(delivery.getBody())));
    }
}

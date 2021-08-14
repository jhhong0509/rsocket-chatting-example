package jhhong.example.rsocketchatting.domain.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ChatServiceImpl implements ChatService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public Mono<Void> joinRoom(String roomId) {
        return rabbitTemplate.convertAndSend("");
    }

    @Override
    public Mono<Void> sendMessage(String message, String roomId) {
        return null;
    }
}

package jhhong.example.rsocketchatting.domain.rabbit.sender;

import jhhong.example.rsocketchatting.domain.chat.payload.ChatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreMessageSender {
    private final RabbitTemplate template;
    private final FanoutExchange exchange;

    public void send(ChatRequest request) {
        template.send(exchange.getName(), "", template.getMessageConverter().toMessage(request, new MessageProperties()));
    }
}

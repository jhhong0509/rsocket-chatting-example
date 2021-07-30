package jhhong.example.rsocketchatting.domain.chat.router;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("send")
    public Mono<Void> sendMessage(String message) {
        return chatService.sendMessage(message);
    }

    @MessageMapping("message")
    public Flux<Chat> getMessage() {
        return chatService.getMessage();
    }

}

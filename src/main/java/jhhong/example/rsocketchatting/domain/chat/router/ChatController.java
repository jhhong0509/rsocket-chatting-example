package jhhong.example.rsocketchatting.domain.chat.router;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.entity.ChatRepository;
import jhhong.example.rsocketchatting.domain.chat.entity.Message;
import jhhong.example.rsocketchatting.domain.chat.exception.ChatRoomNotFoundException;
import jhhong.example.rsocketchatting.domain.chat.service.ChatService;
import jhhong.example.rsocketchatting.domain.user.entity.UserRepository;
import jhhong.example.rsocketchatting.global.security.auth.AuthenticationExportManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("send")
    public Mono<Void> sendMessage(String chatRoomId, String message) {
        return chatService.sendMessage(chatRoomId, message);
    }

    @MessageMapping("message")
    public Flux<Message> getMessage(String chatRoomId) {
        return chatService.getMessage(chatRoomId);
    }

}

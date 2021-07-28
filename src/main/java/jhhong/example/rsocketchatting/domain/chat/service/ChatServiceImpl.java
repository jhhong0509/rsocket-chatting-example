package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.entity.ChatRepository;
import jhhong.example.rsocketchatting.domain.chat.entity.Message;
import jhhong.example.rsocketchatting.domain.chat.exception.ChatRoomNotFoundException;
import jhhong.example.rsocketchatting.domain.user.entity.UserRepository;
import jhhong.example.rsocketchatting.global.security.auth.AuthenticationExportManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final AuthenticationExportManager authenticationManager;

    @Override
    public Mono<Void> sendMessage(String chatRoomId, String message) {
        return chatRepository.findById(chatRoomId)
                .zipWith(authenticationManager.getUserId()
                        .flatMap(userRepository::findById))
                .doOnNext(objects -> objects.getT1().getMessage().add(new Message(message,
                        objects.getT2().getEmail(), objects.getT2().getNickname())))
                .flatMap(tuple2 -> chatRepository.save(tuple2.getT1()))
                .switchIfEmpty(Mono.error(ChatRoomNotFoundException::new))
                .then();
    }

    @Override
    public Flux<Message> getMessage(String chatRoomId) {
        return chatRepository.findById(chatRoomId)
                .map(Chat::getMessage)
                .flatMapIterable(msg -> msg);
    }
}

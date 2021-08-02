package jhhong.example.rsocketchatting.domain.chat.service;

import jhhong.example.rsocketchatting.domain.chat.adapter.outbound.UserAdapter;
import jhhong.example.rsocketchatting.domain.chat.entity.Chat;
import jhhong.example.rsocketchatting.domain.chat.entity.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserAdapter userAdapter;

    @Override
    public Flux<Chat> sendMessage(Flux<String> message) {
        System.out.println("오 이거 어디감");
        return message
                .map(msg -> Chat.builder()
                                .senderName("name")
                                .senderEmail("email")
                                .message(msg)
                                .build())
                .flatMap(chatRepository::save);
//        return ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .map(Authentication::getName)
//                .flatMap(userAdapter::getUserInfo)
//                .doOnNext(System.out::println)
//                .map(user -> Chat.builder()
//                        .message(message)
//                        .senderEmail(user.getEmail())
//                        .senderName(user.getNickname())
//                        .build())
//                .flatMap(chatRepository::save)
//                .then();
//        return chatRepository.findById(chatRoomId)
//                .zipWith(authenticationManager.getUserId()
//                        .flatMap(userRepository::findById))
//                .doOnNext(objects -> objects.getT1().getMessage().add(new Message(message,
//                        objects.getT2().getEmail(), objects.getT2().getNickname())))
//                .flatMap(tuple2 -> chatRepository.save(tuple2.getT1()))
//                .switchIfEmpty(Mono.error(ChatRoomNotFoundException::new))
//                .then();
    }

    @Override
    public Flux<Chat> getMessage() {
        return chatRepository.findAll();
    }
}

package jhhong.example.rsocketchatting.domain.chat.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    @Tailable               // MongoDB는 Tailable Cursor를 Capped Collection에서만 지원한다.
    Flux<Chat> findAllBy();
}

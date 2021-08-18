package jhhong.example.rsocketchatting.domain.chatroom.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom, String> {
    Flux<ChatRoom> findAllByMembersContaining(String member);
}

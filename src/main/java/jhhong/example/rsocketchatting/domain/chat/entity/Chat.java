package jhhong.example.rsocketchatting.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@Document
public class Chat {

    @MongoId
    private final String id;

    private final String roomId;

    private final String roomName;

    private final List<Message> message;

}

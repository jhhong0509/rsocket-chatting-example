package jhhong.example.rsocketchatting.domain.chatroom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class ChatRoom {

    @MongoId
    private String id;

    private String roomName;

    private List<String> members;

}

package jhhong.example.rsocketchatting.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Chat {

    @MongoId
    private String id;

    private String chatRoomId;

    private String message;

    private String senderEmail;

    private String senderName;

    @CreatedDate
    private LocalDateTime createdAt;

}

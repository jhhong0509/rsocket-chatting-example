package jhhong.example.rsocketchatting.domain.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Message {

    private final String message;

    private final String senderEmail;

    private final String senderName;

}

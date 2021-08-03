package jhhong.example.rsocketchatting.domain.chat.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRequest {
    private final String content;
}

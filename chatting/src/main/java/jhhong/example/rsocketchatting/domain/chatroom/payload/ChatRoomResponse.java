package jhhong.example.rsocketchatting.domain.chatroom.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomResponse {

    private String chatRoomName;

    private String chatRoomId;

    private String lastMessage;

    private boolean isRead;

}

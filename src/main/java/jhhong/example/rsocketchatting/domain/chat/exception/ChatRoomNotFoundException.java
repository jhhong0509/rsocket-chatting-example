package jhhong.example.rsocketchatting.domain.chat.exception;

import jhhong.example.rsocketchatting.global.error.ErrorCode;
import jhhong.example.rsocketchatting.global.error.GlobalException;

public class ChatRoomNotFoundException extends GlobalException {
    public ChatRoomNotFoundException() {
        super(ErrorCode.CHATROOM_NOT_FOUND);
    }
}

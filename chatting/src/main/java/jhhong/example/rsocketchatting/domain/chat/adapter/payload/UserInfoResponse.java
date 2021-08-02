package jhhong.example.rsocketchatting.domain.chat.adapter.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private final String email;

    private final String nickname;
}

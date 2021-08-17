package jhhong.example.rsocketchatting.global.adapter.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private final String email;

    private final String nickname;
}

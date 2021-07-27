package jhhong.example.rsocketchatting.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@AllArgsConstructor
@Builder
@Document
public class User {

    @MongoId
    private final String email;

    @NonNull
    private final String password;

    @NonNull
    private final String nickname;
}

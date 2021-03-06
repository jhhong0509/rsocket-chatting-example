package jhhong.example.user.domain.user.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CreateUserRequest {

    @Email
    private final String email;

    @NotBlank
    private final String password;

    @NotBlank
    private final String nickName;
}

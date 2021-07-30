package jhhong.example.user.domain.auth.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class AuthRequest {

    @Email
    private final String email;

    @NotBlank
    private final String password;

}

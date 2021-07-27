package jhhong.example.rsocketchatting.global.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final String email;                         // principal = id
    private final String token;                         // credentials = password

    public AuthenticationToken(String email, String token) {
        super(null);
        super.setAuthenticated(true);
        this.email = email;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }
}

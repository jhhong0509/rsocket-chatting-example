package jhhong.example.rsocketchatting.global.security.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String token;

    public AuthenticationToken(String email, String token) {
        super(null);
        super.setAuthenticated(true);
        this.email = email;
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return email;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
}

package jhhong.example.rsocketchatting.global.security.config;

import jhhong.example.rsocketchatting.global.adapter.payload.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class AuthDetails implements UserDetails {

    private final UserInfoResponse userInfoResponse;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return userInfoResponse.getNickname();
    }

    @Override
    public String getUsername() {
        return userInfoResponse.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

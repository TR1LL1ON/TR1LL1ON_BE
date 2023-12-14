package com.ybe.tr1ll1on.security.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
public class PrincipalDetails implements UserDetails {

    private final String username;
    private final String password;
    private final String authority;
    private final Collection<GrantedAuthority> authorities;

    @Builder
    private PrincipalDetails(
            String username, String password, String authority, Collection<GrantedAuthority> authorities
    ) {
        this.username = username;
        this.password = password;
        this.authority = authority;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
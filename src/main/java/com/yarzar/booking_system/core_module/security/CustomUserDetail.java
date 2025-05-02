package com.yarzar.booking_system.core_module.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetail implements UserDetails {

    private final String name;

    private final String email;

    private final String password;

    private final boolean enabled;

    private final Collection<? extends GrantedAuthority> authorities;

    private CustomUserDetail(String name,
                             String email,
                             String password,
                             boolean enabled,
                             final Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public static CustomUserDetail create(String name,
                                          String email,
                                          String password,
                                          boolean enabled,
                                          final Collection<? extends GrantedAuthority> authorities) {
        return new CustomUserDetail(name, email, password, enabled, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.enabled;
    }
}

package com.devolution.assurelle_api.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.devolution.assurelle_api.model.entity.UserAccount;

public class UserAccountDetails implements UserDetails {
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    public UserAccountDetails(UserAccount userAccount) {
        name = userAccount.getName();
        password = userAccount.getPassword();
        authorities = Arrays.stream(userAccount.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
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
        return name;
    }
}

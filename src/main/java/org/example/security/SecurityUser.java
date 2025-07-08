package org.example.security;

import org.example.model.entity.UserProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final SimpleGrantedAuthority simpleGrantedAuthority;


    public SecurityUser(UserProfile userProfile) {
        this.username = userProfile.getUsername();
        this.password = userProfile.getPassword();
        this.simpleGrantedAuthority = new SimpleGrantedAuthority(userProfile.getRole().toString());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

}

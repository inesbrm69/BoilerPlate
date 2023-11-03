package com.ynov.boilerplate.entity;

import com.ynov.boilerplate.entity.Enum.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    @Transient
    private static final String SEQUENCE_NAME ="user_ sequence";

    @Id
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roles != null){
            return List.of(new SimpleGrantedAuthority(roles.name()));
        }else{
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

package com.green.greengram4.security;

import com.green.greengram4.user.model.UserEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {//순서대로 locallogin, sociallogin에 쓰임

    private MyPrincipal myPrincipal; //jwt token만들때 객체자체를 토큰에박아버려서 필요함
    private Map<String, Object> attributes;
    private UserEntity userEntity;//accessToken iuser, nm,pic,firebasetoken을 주고있는데


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(myPrincipal == null) {
            return null;
        }
        return this.myPrincipal.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return null; }

    @Override
    public String getUsername() { return userEntity.getUid(); }//security를 타서 null이 return되면안됨

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }
}

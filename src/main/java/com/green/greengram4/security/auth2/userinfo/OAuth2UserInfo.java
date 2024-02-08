package com.green.greengram4.security.auth2.userinfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
//facebook, naver 다 여기에 상속받아서 구조를 만드는것

@AllArgsConstructor
@Getter
public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public abstract String getId();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String getImageUrl();

}

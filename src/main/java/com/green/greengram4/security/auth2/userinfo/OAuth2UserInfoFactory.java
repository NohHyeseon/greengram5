package com.green.greengram4.security.auth2.userinfo;

import com.green.greengram4.security.auth2.SocialProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;

//Spring 에서 factory는 객체화를 뜻함 객체생성하는 공장
//socialProvider 타입에 따라 네이버를 만들지 카카오를 만들지 정해줌
@Component
public class OAuth2UserInfoFactory {
    public OAuth2UserInfo getOAuth2userInfo(SocialProviderType socialProviderType, Map<String, Object> attrs){
        return switch (socialProviderType){
            case KAKAO -> new KaKaoOAuth2UserInfo(attrs);
            case NAVER -> new NaverOAuth2UserInfo(attrs);
            default -> throw  new IllegalArgumentException("Invalid Provider Type.");
        };

    }
}

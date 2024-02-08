package com.green.greengram4.security.auth2.userinfo;

//카카오가 OAuth2UserInfo 상속받기위함

import java.util.Map;

public class KaKaoOAuth2UserInfo extends OAuth2UserInfo {

    public KaKaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }


    @Override
    public String getId() {
        return attributes.get("id").toString(); //pk값과 비슷하다고보면됨
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties"); //네이버 속성때문에
        //프로퍼티 밑에 name이 있어서 필요한 과정

        return properties == null ? null : properties.get("nickname").toString();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("account_email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return properties == null ? null : (String) properties.get("thumbnail_image");
    }
}

package com.green.greengram4.security.auth2.userinfo;

import java.util.Map;

//네이버 Json형태 "response":{
// "id" : "~~~",
public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes); // 생성자부터 만들어주기
    }

    @Override
    public String getId() {
        Map<String, Object> res = (Map<String, Object>) attributes.get("response");
        return res == null ? null : (String) res.get("id");
    }

    @Override
    public String getName() {
        Map<String, Object> res = (Map<String, Object>) attributes.get("response");
        return res == null ? null : (String) res.get("name");
    }

    @Override
    public String getEmail() {
        Map<String, Object> res = (Map<String, Object>) attributes.get("response");
        return res == null ? null : (String) res.get("email");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> res = (Map<String, Object>) attributes.get("response");
        return res == null ? null : (String) res.get("profile_image");
    }
}

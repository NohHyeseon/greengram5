package com.green.greengram4.security.auth2;

import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.auth2.SocialProviderType;
import com.green.greengram4.security.auth2.userinfo.OAuth2UserInfo;
import com.green.greengram4.security.auth2.userinfo.OAuth2UserInfoFactory;
import com.green.greengram4.user.UserMapper;
import com.green.greengram4.user.model.UserEntity;
import com.green.greengram4.user.model.UserSelDto;
import com.green.greengram4.user.model.UserSignupProcDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory factory;




    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest); //호출당했을때 부모꺼의 loadUser전부 실행하겠다
        try {
            return this.process(userRequest, user); //process 를통해 이차 가공하겠다 위에꺼 실행하고
        }catch (AuthenticationException e){
            throw e;
        }catch (Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }


    }
    public OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user){
        SocialProviderType socialProviderType = //socialProviderType에 카카오, 네이버가 들어가는것
                SocialProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        // ResgistraionId앞까지가 등록되있는거고 ID부터 kakao, naver부분
        //valueOf의; 리턴타입 socialprovidertype

         //user.getAttributes();//통신 후 가져온 자료들이 들어가있음 맵형식으로

        OAuth2UserInfo oAuth2UserInfo = factory.getOAuth2userInfo(socialProviderType, user.getAttributes());
        UserSelDto dto = UserSelDto.builder()
                .providerType(socialProviderType.name())
                .uid(oAuth2UserInfo.getId())
                .build();
        UserEntity savedUser = mapper.selUser(dto); //null이면 소셜로그인으로 한번도 로그인한적없다
        if(savedUser == null){ //회원가입처리하겠다
            savedUser = signupUser(oAuth2UserInfo, socialProviderType);

        }

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(savedUser.getIuser())
                .build();
        myPrincipal.getRoles().add(savedUser.getRole());

        return MyUserDetails.builder()
                .userEntity(savedUser)
                .attributes(user.getAttributes())
                .myPrincipal(myPrincipal)
                .build();
    }

    private UserEntity signupUser(OAuth2UserInfo oAuth2UserInfo, SocialProviderType socialProviderType){
        UserSignupProcDto dto = new UserSignupProcDto();
        dto.setProviderType(socialProviderType.name());
        dto.setUid(oAuth2UserInfo.getId());//kakao, naver에서의 아이디
        dto.setUpw("social");
        dto.setNm(oAuth2UserInfo.getName());
        dto.setPic(oAuth2UserInfo.getImageUrl());
        dto.setRole("USER");
        int result = mapper.insUser(dto);

        UserEntity entity = new UserEntity();
        entity.setIuser(dto.getIuser());
        entity.setUid(dto.getUid());
        entity.setRole(dto.getRole());
        entity.setNm(dto.getNm());
        entity.setPic(dto.getPic());
        return entity;

    }


}

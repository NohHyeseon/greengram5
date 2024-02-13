package com.green.greengram4.security.auth2;

import com.green.greengram4.common.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.green.greengram4.security.auth2.OAuth2AuthenticationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final CookieUtils cookieUtils;
    private final OAuth2AuthenticationRequestBasedOnCookieRepository repository;//dao같은,,역할 저장하는역할,,

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        log.info("OAuth2AuthenticationFailureHandler - onAuthenticationFailure");
        String targetUrl = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse("/");

        exception.printStackTrace();

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage()) //지역에맞는 에러메세지문구 근데 영어로나옴
                .build()
                .toUriString();


        repository.removeAuthorizationRequestCookies(response);

        getRedirectStrategy().sendRedirect(request,response,targetUrl);//주소값이동

    }
}

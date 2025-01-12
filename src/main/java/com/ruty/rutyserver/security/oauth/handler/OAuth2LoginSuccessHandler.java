package com.ruty.rutyserver.security.oauth.handler;

import com.ruty.rutyserver.member.MemberRepository;
import com.ruty.rutyserver.member.MemberRole;
import com.ruty.rutyserver.security.jwt.JwtService;
import com.ruty.rutyserver.security.oauth.dto.common.CustomOAuth2Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();
//            loginSuccess(response, oAuth2Member); // 로그인에 성공한 경우 access, refresh 토큰 생성

            String email = oAuth2Member.getEmail();
            String authority = authentication.getAuthorities().iterator().next().getAuthority();

            String access = jwtService.createAccessToken(email);
            String refresh = jwtService.createRefreshToken();
            response.addCookie(createCookie("Authorization", access));
            response.addCookie(createCookie("Authorization-refresh", refresh));
            response.sendRedirect("http://34.59.214.93:8080/auth/googleLogin");
            jwtService.updateRefreshToken(oAuth2Member.getEmail(), refresh);

        } catch (Exception e) {
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2Member oAuth2Member) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2Member.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2Member.getEmail(), refreshToken);
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}

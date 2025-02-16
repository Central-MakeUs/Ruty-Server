package com.ruty.rutyserver.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.e.MemberRole;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginService {
    private final GoogleOauthMember googleOauthMember;
    private final AppleOauthMember appleOauthMember;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final Map<String, Boolean> codeProcessed = new HashMap<>();  // 새로운 코드 처리 여부를 추적

    public JwtDto loginSocial(String source, SocialLoginDto socialLoginDto)
            throws GeneralSecurityException, IOException {
        if (codeProcessed.getOrDefault(socialLoginDto.getCode(), false)) {
            log.info("이미 처리된 code: " + socialLoginDto.getCode());
            return null;  // 이미 처리된 code라면 null 반환
        }

        SocialType socialType = SocialType.validSocialType(source);

        String resourceServerAccessToken;
        Map<String, Object> memberInfo;
        if(socialType.equals(SocialType.GOOGLE)) {
            memberInfo = googleOauthMember.getAccessToken(socialType, socialLoginDto.getPlatformType(), socialLoginDto.getCode());
        } else {
            resourceServerAccessToken = appleOauthMember.getAccessToken(socialType, socialLoginDto.getCode());
            memberInfo = appleOauthMember.getMemberInfo(socialType.getValue(), resourceServerAccessToken);
        }

        // 로그인 성공 시 코드 처리 완료로 설정
        codeProcessed.put(socialLoginDto.getCode(), true);

        return saveOrUpdateMember(memberInfo, socialType.getValue());
    }


    private JwtDto saveOrUpdateMember(Map<String, Object> memberInfo, String provider) {
        String email = (String) memberInfo.get("email");
        String name = (String) memberInfo.get("name");

        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .email(email)
                        .name(name)
                        .socialType(SocialType.validSocialType(provider))
                        .role(MemberRole.ROLE_MEMBER)
                        .refreshToken(refreshToken)
                        .build()));

        return new JwtDto(accessToken, refreshToken);
    }
}

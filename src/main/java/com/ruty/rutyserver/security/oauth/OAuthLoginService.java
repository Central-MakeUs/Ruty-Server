package com.ruty.rutyserver.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final GoogleOauthMember googleOauthMember;
    private final AppleOauthMember appleOauthMember;

    public String loginSocial(SocialType socialType, String code) {
        String accessToken;
        if(socialType.equals("google")) {
            accessToken = googleOauthMember.getAccessToken(socialType, code);
        } else {
            accessToken = appleOauthMember.getAccessToken(socialType, code);
        }
        Map<String, Object> userInfo = getUserInfo(socialType.getValue(), accessToken);

        return saveOrUpdate(userInfo, socialType);
    }
}

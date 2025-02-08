package com.ruty.rutyserver.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOauthMember implements OauthMember{
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String APPLE_TOKEN_URI = "https://appleid.apple.com/auth/token";

    @Value("${spring.security.oauth2.client.registration.apple.client-id}")
    private String appleClientId;

    @Value("${spring.security.oauth2.client.registration.apple.client-secret}")
    private String appleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String appleRedirectUri;

    @Value("${spring.security.oauth2.client.provider.apple.authorization-uri}")
    private String appleAuthorizationUri;

    @Value("${spring.security.oauth2.client.provider.apple.token-uri}")
    private String appleTokenUri;

    @Override
    public String getAccessToken(SocialType socialType, String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", appleClientId);
        params.add("client_secret", appleClientSecret);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        String tokenUri = socialType.getValue().equals("google") ? GOOGLE_TOKEN_URI : APPLE_TOKEN_URI;

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, entity, String.class);
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse access token response");
        }
    }

    @Override
    public Map<String, Object> getMemberInfo(String provider, String accessToken) {
        return null;
    }

    @Override
    public String saveOrUpdateMember(Map<String, Object> userInfo, String provider) {
        return null;
    }
}

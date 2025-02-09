package com.ruty.rutyserver.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOauthMember implements OauthMember {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    @Override
    public String getAccessToken(SocialType socialType, String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", googleRedirectUri);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_TOKEN_URI, entity, String.class);

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            return jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse access token response");
        }
    }

    @Override
    public Map<String, Object> getMemberInfo(String provider, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USER_INFO_URI, HttpMethod.GET, entity, String.class);
        try {
            return objectMapper.readValue(response.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse user info response");
        }
    }

}

package com.ruty.rutyserver.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ruty.rutyserver.entity.e.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleOauthMember {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String GOOGLE_TOKEN_URI = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_USER_INFO_URI = "https://www.googleapis.com/oauth2/v2/userinfo";

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String androidGoogleClientId;

    @Value("${custom.google.ios-client-id}")
    private String iosGoogleClientId;

    @Value("${custom.google.ios-redirect-uri}")
    private String iosGoogleRedirectUri;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;

    public Map<String, Object> getAccessToken(SocialType socialType, String platformType, String idToken)
            throws GeneralSecurityException, IOException {

        System.out.println("idToken = " + idToken);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(platformType.equals("ios") ? iosGoogleClientId : androidGoogleClientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);

        // 유효하지 않은 ID 토큰일 경우 예외 처리
        if (googleIdToken == null) {
            throw new IllegalArgumentException("Invalid ID token");
        }

        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        String email = payload.getEmail();
        String name =  (String) payload.get("name");
        String picture = (String) payload.get("picture");

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("email", email);
        userDetails.put("name", name);
        userDetails.put("picture", picture);

        return userDetails;


//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        if(platformType.equals("ios")) {
//            params.add("client_id", iosGoogleClientId);
//            params.add("redirect_uri", iosGoogleRedirectUri);
//        }
//        else {
//            params.add("client_id", androidGoogleClientId);
//            params.add("client_secret", googleClientSecret);
//            params.add("redirect_uri", googleRedirectUri);
//        }
//        params.add("code", code);
//        params.add("grant_type", "authorization_code");
//
//        System.out.println("code = " + code);
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_TOKEN_URI, entity, String.class);
//
//        try {
//            JsonNode jsonNode = objectMapper.readTree(response.getBody());
//            return jsonNode.get("access_token").asText();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to parse access token response");
//        }
    }

    public Map<String, Object> getMemberInfo(String provider, String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(GOOGLE_USER_INFO_URI, HttpMethod.GET, entity, String.class);

        System.out.println("response = " + response.getBody());
        try {
            return objectMapper.readValue(response.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse user info response");
        }
    }

}

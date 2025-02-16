package com.ruty.rutyserver.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.repository.MemberRepository;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppleOauthMember{
    private final RestTemplate restTemplate;

//    private static final String APPLE_TOKEN_URI = "https://appleid.apple.com/auth/token";
    private static final String APPLE_AUTHORIZATION_URI = "https://appleid.apple.com/auth/authorize?response_mode=form_post";
    private static final String APPLE_TOKEN_URI = "https://appleid.apple.com/auth/oauth2/v2/token";

    @Value("${spring.security.oauth2.client.registration.apple.client-id}")
    private String appleClientId;

    @Value("${spring.security.oauth2.client.registration.apple.client-secret}")
    private String appleClientSecret;

    @Value("${spring.security.oauth2.client.registration.apple.redirect-uri}")
    private String appleRedirectUri;

    public String getAccessToken(SocialType socialType, String code) {

        String clientSecret = createAppleClientSecret();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "com.jaySeong.Ruty");
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", appleRedirectUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> appleTokenResponse = restTemplate.exchange(APPLE_TOKEN_URI, HttpMethod.POST, request, Map.class);
        // 전체 응답 로그 출력
        log.info("Response Status: {}", appleTokenResponse.getStatusCode());
        log.info("Response Headers: {}", appleTokenResponse.getHeaders());
        log.info("Response Body: {}", appleTokenResponse.getBody()); // 객체가 null일 수도 있으므로 체크 필요

        Map<String, Object> appleToken = appleTokenResponse.getBody();

        if (appleToken == null || appleToken.get("id_token") == null) {
            log.error("애플 로그인 실패 - ID Token이 없습니다. Response Body: {}", appleTokenResponse.getBody());
            throw new RuntimeException("애플 로그인 실패 - ID Token이 없습니다.");
        }


        return (String) appleToken.get("id_token");
    }

    public Map<String, Object> getMemberInfo(String provider, String idToken) {
        String[] jwtParts = idToken.split("\\.");
        if (jwtParts.length < 2) {
            throw new RuntimeException("Invalid ID Token from Apple");
        }

        String payloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]));
        log.info("Apple ID Token Payload: {}", payloadJson);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(payloadJson, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Apple ID Token", e);
        }
    }
    private String createAppleClientSecret() {
        String clientSecret = "";
        //application-oauth.yml에 설정해놓은 apple secret Key를 /를 기준으로 split
        String[] secretKeyResourceArr = appleClientSecret.split("/");
        try {
            InputStream inputStream = new ClassPathResource("static/" + secretKeyResourceArr[0]).getInputStream();
            File file = File.createTempFile("AuthKey_6K9W84675H",".p8");
            try {
                FileUtils.copyInputStreamToFile(inputStream, file);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            String appleKeyId = secretKeyResourceArr[1];
            String appleTeamId = secretKeyResourceArr[2];

            PEMParser pemParser = new PEMParser(new FileReader(file));
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemParser.readObject();

            PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);

            clientSecret = Jwts.builder()
                    .setHeaderParam(JwsHeader.KEY_ID, appleKeyId)
                    .setIssuer(appleTeamId)
                    .setAudience("https://appleid.apple.com")
                    .setSubject("com.jaySeong.Ruty")
                    .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .signWith(privateKey, Jwts.SIG.ES256)
                    .compact();
        } catch (IOException e) {
            log.error("Error_createAppleClientSecret : {}-{}", e.getMessage(), e.getCause());
        }
        log.info("createAppleClientSecret : {}", clientSecret);
        return clientSecret;
    }
}

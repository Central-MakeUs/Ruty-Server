package com.ruty.rutyserver.security.oauth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruty.rutyserver.domain.member.entity.Member;
import com.ruty.rutyserver.domain.member.repository.MemberRepository;
import com.ruty.rutyserver.security.oauth.dto.common.CustomOAuth2Member;
import com.ruty.rutyserver.security.oauth.dto.common.OAuthAttributes;
import com.ruty.rutyserver.security.oauth.dto.common.SocialType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private static final String GOOGLE = "google";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        // 로그인 진행 중인 서비스를 구분
        // 네이버로 로그인 진행 중인지, 구글로 로그인 진행 중인지, ... 등을 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //Apple의 경우 id_token에 회원정보가 있으므로 회원정보 API 호출과정 생략
        Map<String, Object> attributes;
        if(registrationId.contains("apple")){
            String idToken = userRequest.getAdditionalParameters().get("id_token").toString();
            attributes = decodeJwtTokenPayload(idToken);
            attributes.put("id_token", idToken);
        }else{
            OAuth2User oAuth2User = delegate.loadUser(userRequest);
            attributes = oAuth2User.getAttributes();
        }
        SocialType socialType = getSocialType(registrationId);
        // OAuth2 로그인 진행 시 키가 되는 필드 값(Primary Key와 같은 의미)
        // 구글의 경우 기본적으로 코드를 지원
        // 하지만 네이버, 카카오 등은 기본적으로 지원 X
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // -> apple로 하면 여기가 비어있음.attributes의 sub값 쓰는게 나을듯.

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute 등을 담을 클래스
        // socialtype에 따라 google이나 apple 객체 생성함.
        OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

        // 사용자 저장 또는 업데이트
        Member member = saveOrUpdate(extractAttributes, socialType);

        return new CustomOAuth2Member(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())),
                attributes,
                extractAttributes.getNameAttributeKey(),
                member.getNickName(),
                member.getEmail(),
                member.getRole()
        );
    }

    //JWT Payload부분 decode 메서드
    public Map<String, Object> decodeJwtTokenPayload(String jwtToken){
        Map<String, Object> jwtClaims = new HashMap<>();
        try {
            String[] parts = jwtToken.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            byte[] decodedBytes = decoder.decode(parts[1].getBytes(StandardCharsets.UTF_8));
            String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = mapper.readValue(decodedString, Map.class);
            jwtClaims.putAll(map);

        } catch (JsonProcessingException e) {
            log.error("decodeJwtToken: {}-{} / jwtToken : {}", e.getMessage(), e.getCause(), jwtToken);
        }
        return jwtClaims;
    }

    private Member saveOrUpdate(OAuthAttributes attributes, SocialType socialType) {
        Member member = memberRepository.findByEmail(attributes.getOAuth2MemberInfo().getEmail())
                // 구글 사용자 정보 업데이트(이미 가입된 사용자) => 업데이트
                .map(entity -> entity.update(attributes.getOAuth2MemberInfo().getNickname(), attributes.getOAuth2MemberInfo().getPicture()))
                // 가입되지 않은 사용자 => Member 엔티티 생성
                .orElse(attributes.toMember(socialType, attributes.getOAuth2MemberInfo()));

        return memberRepository.save(member);
    }

    private SocialType getSocialType(String registrationId) {
        if(GOOGLE.equals(registrationId)) {
            return SocialType.GOOGLE;
        }
        return SocialType.APPLE;
    }

}

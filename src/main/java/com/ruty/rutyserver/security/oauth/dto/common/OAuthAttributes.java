package com.ruty.rutyserver.security.oauth.dto.common;

import com.ruty.rutyserver.member.entity.Member;
import com.ruty.rutyserver.member.entity.MemberRole;
import com.ruty.rutyserver.security.oauth.dto.AppleOAuth2MemberInfo;
import com.ruty.rutyserver.security.oauth.dto.GoogleOAuth2MemberInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

// OAuth2의 종류에 따라서 객체를 google의 정보에 혹은 apple의 정보에 맞도록 객체화시킴.
@Getter
public class OAuthAttributes {
    private OAuth2MemberInfo oAuth2MemberInfo;// oauth 로그인시 받은 정보들을 그대로 저장함.
    private String nameAttributeKey; // OAuth2 로그인시, 키가 되는 필드.(=pk)

    @Builder
    public OAuthAttributes(OAuth2MemberInfo oAuth2MemberInfo,
                           String nameAttributeKey) {
        this.oAuth2MemberInfo = oAuth2MemberInfo;
        this.nameAttributeKey = nameAttributeKey;
    }

    // OAuth2User에서 반환하는 사용자 정보는 Map
    // 따라서 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(SocialType socialType,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if(socialType == SocialType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return ofApple(userNameAttributeName, attributes);
    }

    // 구글 생성자
    private static OAuthAttributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(usernameAttributeName)
                .oAuth2MemberInfo(new GoogleOAuth2MemberInfo(attributes))
                .build();
    }

    private static OAuthAttributes ofApple(String usernameAttributeName,
                                           Map<String, Object> attributes) {
        // apple은 따로 nameAttributeKey값을 구현해줘야함. google은 기본제공?
        return OAuthAttributes.builder()
                .nameAttributeKey("sub")
                .oAuth2MemberInfo(new AppleOAuth2MemberInfo(attributes))
                .build();
    }

    // Member 엔티티 생성
    public Member toMember(SocialType socialType, OAuth2MemberInfo oAuth2MemberInfo) {
        return Member.builder()
                .email(oAuth2MemberInfo.getEmail())
                .nickName(oAuth2MemberInfo.getNickname())
                .name(Optional.ofNullable(oAuth2MemberInfo.attributes.get("name"))
                .map(Object::toString)
                .orElse(null))
                .picture(oAuth2MemberInfo.getPicture())
                .role(MemberRole.ROLE_MEMBER)
                .socialType(socialType)
                .build();

//        return User.builder()
//                .socialType(socialType)
//                .socialId(oauth2UserInfo.getId())
//                .email(UUID.randomUUID() + "@socialUser.com")
//                .nickname(oauth2UserInfo.getNickname())
//                .imageUrl(oauth2UserInfo.getImageUrl())
//                .role(Role.GUEST)
//                .build();
    }
}

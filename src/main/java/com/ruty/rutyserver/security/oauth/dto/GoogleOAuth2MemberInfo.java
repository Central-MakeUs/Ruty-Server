package com.ruty.rutyserver.security.oauth.dto;

import com.ruty.rutyserver.security.oauth.dto.common.OAuth2MemberInfo;

import java.util.Map;

// 구글에서 가져온 정보들을 취합해둔 곳
public class GoogleOAuth2MemberInfo extends OAuth2MemberInfo {

    public GoogleOAuth2MemberInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }

    @Override
    public String getPicture() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}


package com.ruty.rutyserver.security.oauth.dto;

import com.ruty.rutyserver.security.oauth.dto.common.OAuth2MemberInfo;

import java.util.Map;

public class AppleOAuth2MemberInfo extends OAuth2MemberInfo {
    public AppleOAuth2MemberInfo(Map<String, Object> attributes) {
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
        return null;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}

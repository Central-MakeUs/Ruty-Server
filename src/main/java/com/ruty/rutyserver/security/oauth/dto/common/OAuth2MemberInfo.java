package com.ruty.rutyserver.security.oauth.dto.common;

import java.util.Map;

// social 타입별로 유저정보를 가져오는 추상 클레스
public abstract class OAuth2MemberInfo {
    protected Map<String, Object> attributes;

    public OAuth2MemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"

    public abstract String getNickname();

    public abstract String getPicture();

    public abstract String getEmail();
}

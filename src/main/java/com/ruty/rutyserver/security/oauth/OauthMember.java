package com.ruty.rutyserver.security.oauth;

import com.ruty.rutyserver.entity.e.SocialType;

import java.util.Map;

public interface OauthMember {
    String getAccessToken(SocialType socialType, String code);
    Map<String, Object> getMemberInfo(String provider, String accessToken);
    String saveOrUpdateMember(Map<String, Object> userInfo, String provider);
}

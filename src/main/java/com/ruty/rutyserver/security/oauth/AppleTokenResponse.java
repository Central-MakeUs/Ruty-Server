package com.ruty.rutyserver.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class AppleTokenResponse {
    private String accessToken;
    private String tokenType;
    private String expriesIn;
    private String refreshToken;
    private String idToken;
}

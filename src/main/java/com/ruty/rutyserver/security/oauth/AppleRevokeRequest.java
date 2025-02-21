package com.ruty.rutyserver.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
public class AppleRevokeRequest {
    private String clientId;
    private String clientSecret;
    private String token;
    private String tokenTypeHint;
}

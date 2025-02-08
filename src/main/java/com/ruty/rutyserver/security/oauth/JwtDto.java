package com.ruty.rutyserver.security.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;


}

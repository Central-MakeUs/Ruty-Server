package com.ruty.rutyserver.security.oauth;

import lombok.Data;

@Data
public class SocialLoginDto {
    private String platformType;
    private String code;
}

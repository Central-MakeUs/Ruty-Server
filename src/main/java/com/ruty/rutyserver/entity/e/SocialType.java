package com.ruty.rutyserver.entity.e;

import com.ruty.rutyserver.exception.SocialTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocialType {
    GOOGLE("google"),
    APPLE("apple");

    private final String value;

    public static SocialType validSocialType(String source) {
        if(source.equals(SocialType.GOOGLE.value)) {
            return SocialType.GOOGLE;
        } else if (source.equals(SocialType.APPLE.value)) {
            return SocialType.APPLE;
        }
        throw new SocialTypeException();
    }
}

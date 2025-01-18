package com.ruty.rutyserver.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum MemberRole {
    ROLE_GUEST("GUEST"),
    ROLE_MEMBER("MEMBER"),
    ROLE_ADMIN("ADMIN");
    private final String key;
}

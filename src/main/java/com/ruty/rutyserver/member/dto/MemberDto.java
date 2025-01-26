package com.ruty.rutyserver.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberDto {
    private String nickName;
    private Boolean isAgree;
}

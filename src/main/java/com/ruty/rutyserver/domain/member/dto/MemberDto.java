package com.ruty.rutyserver.domain.member.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberDto {
    private String nickName;
    private Boolean isAgree;
}

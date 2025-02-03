package com.ruty.rutyserver.dto.member;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberDto {
    private String nickName;
    private Boolean isAgree;
}

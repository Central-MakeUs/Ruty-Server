package com.ruty.rutyserver.domain.member.dto;

import com.ruty.rutyserver.domain.member.entity.Member;
import com.ruty.rutyserver.security.oauth.dto.common.SocialType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberInfoDto {
    private Long memberId;
    private String email;
    private String nickName;
    private String name;
    private String picture;
    private Boolean isAgree;
    private SocialType socialType;
    private String refreshToken;

    public static MemberInfoDto of(Member member) {
        return MemberInfoDto.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .nickName(member.getNickName())
                .name(member.getName())
                .picture(member.getPicture())
                .isAgree(member.getIsAgree())
                .socialType(member.getSocialType())
                .refreshToken(member.getRefreshToken())
                .build();
    }
}

package com.ruty.rutyserver.dto.member;

import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.e.SocialType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class MemberLoginDto {
    private String email;
    private String name;

    public static Member toEntityGoogle(MemberLoginDto memberLoginDto) {
        return Member.builder()
                .email(memberLoginDto.email)
                .name(memberLoginDto.getName())
                .socialType(SocialType.GOOGLE)
                .build();
    }
    public static Member toEntityApple(MemberLoginDto memberLoginDto) {
        return Member.builder()
                .email(memberLoginDto.email)
                .name(memberLoginDto.getName())
                .socialType(SocialType.GOOGLE)
                .build();
    }
}

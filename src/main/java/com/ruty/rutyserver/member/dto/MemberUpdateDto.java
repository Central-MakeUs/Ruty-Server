package com.ruty.rutyserver.member.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDto {
    private String nickName;
    private String picture;
    private Boolean isAgree;


}

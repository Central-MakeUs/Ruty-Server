package com.ruty.rutyserver.member;

import com.ruty.rutyserver.security.oauth.dto.common.SocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String nickName;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String picture;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    private String refreshToken;

    @Builder
    public Member(String email, String nickName,
                  String name, String picture, SocialType socialType,
                  MemberRole role, String refreshToken) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.picture = picture;
        this.role = role;
        this.socialType = socialType;
        this.refreshToken = refreshToken;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

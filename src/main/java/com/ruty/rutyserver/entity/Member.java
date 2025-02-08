package com.ruty.rutyserver.entity;

import com.ruty.rutyserver.common.BaseEntity;
import com.ruty.rutyserver.dto.member.MemberUpdateDto;
import com.ruty.rutyserver.entity.e.MemberRole;
import com.ruty.rutyserver.entity.e.SocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = true, length = 20)
    private String nickName;

    @Column(nullable = true, length = 20)
    private String name;

    @Column(nullable = true)
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    @Column(nullable = true)
    private Boolean isAgree;

    private String refreshToken;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RecommendRoutine> recommendRoutines = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryLevel> categoriesLevels = new ArrayList<>();

    @Builder
    public Member(String email, String nickName,
                  String name, String picture, SocialType socialType,
                  Boolean isAgree, MemberRole role, String refreshToken) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.picture = picture;
        this.role = role;
        this.isAgree = isAgree;
        this.socialType = socialType;
        this.refreshToken = refreshToken;
    }

    public Member update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }
    public Member updateProfile(MemberUpdateDto memberUpdateDto) {
        this.nickName = memberUpdateDto.getNickName();
        this.picture = memberUpdateDto.getPicture();
        this.isAgree = memberUpdateDto.getIsAgree();
        return this;
    }

    public Member signUp(String nickName, boolean isAgree) {
        this.nickName = nickName;
        this.isAgree = isAgree;
        return this;
    }

    public Boolean getIsAgree() {
        return this.isAgree;
    }
    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void signUpNickName(String nickName) {
        this.nickName = nickName;
    }

}

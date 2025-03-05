package com.ruty.rutyserver.security.oauth;

import com.ruty.rutyserver.entity.CategoryLevel;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.e.Categories;
import com.ruty.rutyserver.entity.e.MemberRole;
import com.ruty.rutyserver.entity.e.SocialType;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.security.jwt.JwtService;
import com.ruty.rutyserver.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthLoginService {
    private final GoogleOauthMember googleOauthMember;
    private final AppleOauthMember appleOauthMember;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    private final MemberService memberService;
    private final Map<String, Boolean> codeProcessed = new HashMap<>();  // 새로운 코드 처리 여부를 추적

    @Transactional
    public JwtDto loginSocial(String source, SocialLoginDto socialLoginDto)
            throws GeneralSecurityException, IOException {
        if (codeProcessed.getOrDefault(socialLoginDto.getCode(), false)) {
            log.info("이미 처리된 code: " + socialLoginDto.getCode());
            return null;  // 이미 처리된 code라면 null 반환
        }

        SocialType socialType = SocialType.validSocialType(source);

        String resourceServerAccessToken;
        Map<String, Object> memberInfo;
        if(socialType.equals(SocialType.GOOGLE)) {
            memberInfo = googleOauthMember.getAccessToken(socialType, socialLoginDto.getPlatformType(), socialLoginDto.getCode());
        } else {
            resourceServerAccessToken = appleOauthMember.getAccessToken(socialType, socialLoginDto.getCode());
            memberInfo = appleOauthMember.getMemberInfo(socialType.getValue(), resourceServerAccessToken);
        }

        // 로그인 성공 시 코드 처리 완료로 설정
        codeProcessed.put(socialLoginDto.getCode(), true);

        return saveOrUpdateMember(memberInfo, socialType.getValue());
    }

    public void logout(String jwtToken) {
        // 토큰값에 접근하기
        // payload에서 토큰 만료시간 현재 시간으로 바꾸기
        // 반환하기 및
    }

    private JwtDto saveOrUpdateMember(Map<String, Object> memberInfo, String provider) {
        String email = (String) memberInfo.get("email");
        String name = (String) memberInfo.get("name");

        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> {
                    Member newMember = Member.builder()
                            .email(email)
                            .name(name)
                            .socialType(SocialType.validSocialType(provider))
                            .role(MemberRole.ROLE_MEMBER)
                            .refreshToken(refreshToken)
                            .build();

                    List<CategoryLevel> levels = List.of(
                            CategoryLevel.builder().category(Categories.HOUSE).member(newMember).build(),
                            CategoryLevel.builder().category(Categories.LEISURE).member(newMember).build(),
                            CategoryLevel.builder().category(Categories.SELFCARE).member(newMember).build(),
                            CategoryLevel.builder().category(Categories.MONEY).member(newMember).build()
                    );

                    newMember.getCategoriesLevels().addAll(levels);

                    return memberRepository.save(newMember); // DB에 저장
                });

        // 영속 상태 보장된 member에서 categoriesLevels 가져오기
//        member = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);



        return new JwtDto(accessToken, refreshToken);
    }
}

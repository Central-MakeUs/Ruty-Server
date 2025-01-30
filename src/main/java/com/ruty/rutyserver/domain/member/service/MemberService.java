package com.ruty.rutyserver.domain.member.service;

import com.ruty.rutyserver.domain.member.dto.MemberDto;
import com.ruty.rutyserver.domain.member.dto.MemberInfoDto;
import com.ruty.rutyserver.domain.member.dto.MemberUpdateDto;
import com.ruty.rutyserver.domain.member.entity.Member;
import com.ruty.rutyserver.domain.member.exception.MemberNotFoundException;
import com.ruty.rutyserver.domain.member.repository.MemberRepository;
import com.ruty.rutyserver.domain.recommend.RecommendRepository;
import com.ruty.rutyserver.domain.recommend.RecommendRoutines;
import com.ruty.rutyserver.domain.recommend.dto.RecommendRoutineDto;
import com.ruty.rutyserver.domain.routine.RoutineDto;
import com.ruty.rutyserver.domain.routine.RoutineRepository;
import com.ruty.rutyserver.domain.routine.Routines;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;
    private final RoutineRepository routineRepository;

    @Transactional
    public List<RecommendRoutineDto> getMyRecommendRoutines(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<RecommendRoutines> recommendByMember = recommendRepository.findAllByMemberId(member.getId());
        return recommendByMember.stream()
                .map(RecommendRoutineDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<RoutineDto> getMyRoutines(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<Routines> recommendByMember = routineRepository.findAllByMemberId(member.getId());
        return recommendByMember.stream()
                .map(RoutineDto::of)
                .collect(Collectors.toList());
    }


    @Transactional
    public Long signUpMember(String email, MemberDto memberDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.signUp(memberDto.getNickName(), memberDto.getIsAgree());
        return member.getId();
    }
    public Long updateMemberNickname(String email, MemberDto memberDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.signUpNickName(memberDto.getNickName());
        return member.getId();
    }

    public List<MemberInfoDto> getAllMembers() {
        List<Member> allMembers = memberRepository.findAll();
        if(allMembers.isEmpty())
            throw new MemberNotFoundException();

        return allMembers.stream()
                .map(MemberInfoDto::of)
                .collect(Collectors.toList());
    }

    // 로그인 이후 약관동의를 했는지 확인하기 위함.
    public boolean isMemberAgree(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        return member.getIsAgree();
    }

    public MemberInfoDto getMyProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        return MemberInfoDto.of(member);
    }

    @Transactional
    public MemberInfoDto updateMyProfile(String email, MemberUpdateDto updateDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        Member updateProfile = member.updateProfile(updateDto);
        return MemberInfoDto.of(updateProfile);
    }

    @Transactional
    public Long deleteMyProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(member.getId());
        return member.getId();
    }
}

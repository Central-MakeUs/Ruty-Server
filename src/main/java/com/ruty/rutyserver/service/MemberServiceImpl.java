package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.member.MemberDto;
import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.dto.member.MemberUpdateDto;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.repository.RecommendRepository;
import com.ruty.rutyserver.entity.RecommendRoutine;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final RecommendRepository recommendRepository;

    @Override
    @Transactional
    public List<RecommendRoutineDto> getMyRecommendRoutines(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        List<RecommendRoutine> recommendByMember = recommendRepository.findAllByMemberId(member.getId());
        return recommendByMember.stream()
                .map(RecommendRoutineDto::of)
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public Long signUpMember(String email, MemberDto memberDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.signUp(memberDto.getNickName(), memberDto.getIsAgree());
        return member.getId();
    }

    @Override
    public Long updateMemberNickname(String email, MemberDto memberDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        member.signUpNickName(memberDto.getNickName());
        return member.getId();
    }

    @Override
    public List<MemberInfoDto> getAllMembers() {
        List<Member> allMembers = memberRepository.findAll();
        if(allMembers.isEmpty())
            throw new MemberNotFoundException();

        return allMembers.stream()
                .map(MemberInfoDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isMemberAgree(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        return member.getIsAgree();
    }

    @Override
    public MemberInfoDto getMyProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        return MemberInfoDto.of(member);
    }

    @Override
    @Transactional
    public MemberInfoDto updateMyProfile(String email, MemberUpdateDto updateDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        Member updateProfile = member.updateProfile(updateDto);
        return MemberInfoDto.of(updateProfile);
    }

    @Override
    @Transactional
    public Long deleteMyProfile(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(member.getId());
        return member.getId();
    }
}

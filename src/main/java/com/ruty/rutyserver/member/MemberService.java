package com.ruty.rutyserver.member;

import com.ruty.rutyserver.member.dto.MemberDto;
import com.ruty.rutyserver.member.dto.MemberInfoDto;
import com.ruty.rutyserver.member.entity.Member;
import com.ruty.rutyserver.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
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
}

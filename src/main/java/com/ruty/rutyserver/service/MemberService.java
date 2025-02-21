package com.ruty.rutyserver.service;

import com.ruty.rutyserver.dto.member.MemberDto;
import com.ruty.rutyserver.dto.member.MemberInfoDto;
import com.ruty.rutyserver.dto.member.MemberUpdateDto;
import com.ruty.rutyserver.dto.recommend.RecommendRoutineDto;
import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.RecommendRoutine;
import com.ruty.rutyserver.exception.MemberNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public interface MemberService {

    List<RecommendRoutineDto> getMyRecommendRoutines(String email);

    Long signUpMember(String email, MemberDto memberDto);
    Long updateMemberNickname(String email, MemberDto memberDto);
    List<MemberInfoDto> getAllMembers();

    Boolean isMemberAgree(String email);

    MemberInfoDto getMyProfile(String email);

    MemberInfoDto updateMyProfile(String email, MemberUpdateDto updateDto);

    Long deleteMyProfile(String email, String code);

    void createMemberCategory(String email);
}

package com.ruty.rutyserver.domain.routine;

import com.ruty.rutyserver.domain.member.entity.Member;
import com.ruty.rutyserver.domain.member.exception.MemberNotFoundException;
import com.ruty.rutyserver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final MemberRepository memberRepository;

    public Long saveRoutine(RoutineReq routineReq, String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Routines save = routineRepository.save(Routines.toEntity(routineReq, member));
        return save.getId();
    }

    public List<RoutineDto> getAllRoutines() {
        return routineRepository.findAll()
                .stream()
                .map(RoutineDto::fromEntity) // 엔티티를 DTO로 변환
                .toList(); // 최종 리스트로 변환
        }
}

package com.ruty.rutyserver.member.repository;

import com.ruty.rutyserver.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);
}

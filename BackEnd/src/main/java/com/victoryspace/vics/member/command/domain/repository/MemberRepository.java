package com.victoryspace.vics.member.command.domain.repository;

import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    MemberEntity findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}

package com.victoryspace.vics.voter.command.domain.repository;

import com.victoryspace.vics.voter.command.domain.aggregate.VoterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<VoterEntity, Integer> {
    boolean existsByVoteIdAndMemberId(int voteId, int memberId);
}

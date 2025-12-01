package com.victoryspace.vics.vote.command.domain.repository;

import com.victoryspace.vics.vote.command.domain.aggregate.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {
}

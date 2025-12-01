package com.victoryspace.vics.likes.command.domain.repository;

import com.victoryspace.vics.likes.command.domain.aggregate.LikesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<LikesEntity, Integer> {
}

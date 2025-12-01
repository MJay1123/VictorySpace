package com.victoryspace.vics.comment.command.domain.repository;

import com.victoryspace.vics.comment.command.domain.aggregate.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}

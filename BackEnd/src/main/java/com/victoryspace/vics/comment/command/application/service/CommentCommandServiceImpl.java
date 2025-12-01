package com.victoryspace.vics.comment.command.application.service;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
import com.victoryspace.vics.comment.command.application.mapper.CommentCommandMapper;
import com.victoryspace.vics.comment.command.domain.aggregate.CommentEntity;
import com.victoryspace.vics.comment.command.domain.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentCommandServiceImpl implements CommentCommandService {
    private final CommentRepository repository;
    private final CommentCommandMapper mapper;

    @Override
    public CommentCommandDTO createComment(CommentCommandDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setVoteId(dto.getVoteId());
        entity.setMemberId(dto.getMemberId());
        entity.setContent(dto.getContent());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        CommentEntity createdEntity = repository.save(entity);
        return mapper.toDto(createdEntity);
    }

    @Override
    public CommentCommandDTO updateComment(Integer id, CommentCommandDTO dto) {
        CommentEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        entity.setContent(dto.getContent());
        entity.setUpdatedAt(LocalDateTime.now());
        CommentEntity updatedMemberEntity = repository.save(entity);
        return mapper.toDto(updatedMemberEntity);
    }

    @Override
    public CommentCommandDTO deleteComment(Integer id) {
        CommentEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setDeletedAt(LocalDateTime.now());
        CommentEntity deletedEntity = repository.save(entity);
        return mapper.toDto(deletedEntity);
    }
}

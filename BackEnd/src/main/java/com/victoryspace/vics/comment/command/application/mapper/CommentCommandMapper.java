package com.victoryspace.vics.comment.command.application.mapper;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
import com.victoryspace.vics.comment.command.domain.aggregate.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentCommandMapper {
    public CommentCommandDTO toDto(CommentEntity entity) {
        CommentCommandDTO dto = new CommentCommandDTO();
        dto.setId(entity.getId());
        dto.setVoteId(entity.getVoteId());
        dto.setMemberId(entity.getMemberId());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        return dto;
    }
}

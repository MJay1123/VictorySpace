package com.victoryspace.vics.comment.command.application.mapper;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentCreateResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentDeleteResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentUpdateResponseDTO;
import com.victoryspace.vics.comment.command.domain.aggregate.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentCommandMapper {
    public CommentCreateResponseDTO toCreateResponseDto(CommentEntity entity) {
        return CommentCreateResponseDTO.builder()
                .id(entity.getId())
                .voteId(entity.getVoteId())
                .memberId(entity.getMemberId())
                .content(entity.getContent())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public CommentUpdateResponseDTO toUpdateResponseDto(CommentEntity entity) {
        return CommentUpdateResponseDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public CommentDeleteResponseDTO toDeleteResponseDto(CommentEntity entity) {
        return CommentDeleteResponseDTO.builder()
                .id(entity.getId())
                .deletedAt(entity.getDeletedAt())
                .build();
    }
}

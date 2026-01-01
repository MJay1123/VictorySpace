package com.victoryspace.vics.comment.command.application.service;

import com.victoryspace.vics.comment.command.application.dto.CommentCommandDTO;
import com.victoryspace.vics.comment.command.application.dto.request.CommentCreateRequestDTO;
import com.victoryspace.vics.comment.command.application.dto.request.CommentUpdateRequestDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentCreateResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentDeleteResponseDTO;
import com.victoryspace.vics.comment.command.application.dto.response.CommentUpdateResponseDTO;
import com.victoryspace.vics.comment.command.application.mapper.CommentCommandMapper;
import com.victoryspace.vics.comment.command.domain.aggregate.CommentEntity;
import com.victoryspace.vics.comment.command.domain.repository.CommentRepository;
import com.victoryspace.vics.comment.exception.CommentException;
import com.victoryspace.vics.common.error.ErrorCode;
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
    public CommentCreateResponseDTO createComment(CommentCreateRequestDTO requestDTO) {
        CommentEntity entity = CommentEntity.create(
                requestDTO.getVoteId(),
                requestDTO.getMemberId(),
                requestDTO.getContent()
        );
        return mapper.toCreateResponseDto(repository.save(entity));
    }

    @Override
    public CommentUpdateResponseDTO updateComment(Integer id, CommentUpdateRequestDTO requestDTO) {
        CommentEntity entity = repository.findById(id)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
        entity.update(requestDTO.getContent());
        return mapper.toUpdateResponseDto(repository.save(entity));
    }

    @Override
    public CommentDeleteResponseDTO deleteComment(Integer id) {
        CommentEntity entity = repository.findById(id)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND));
        entity.delete();
        return mapper.toDeleteResponseDto(repository.save(entity));
    }
}

package com.victoryspace.vics.likes.command.application.mapper;

import com.victoryspace.vics.likes.command.application.dto.response.LikesCreateResponseDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesDeleteResponseDTO;
import com.victoryspace.vics.likes.command.domain.aggregate.LikesEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LikesCommandMapper {

    public LikesCreateResponseDTO toCreateResponseDTO(LikesEntity entity) {
        return LikesCreateResponseDTO.builder()
                .id(entity.getId())
                .voteId(entity.getVoteId())
                .memberId(entity.getMemberId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    public LikesDeleteResponseDTO toDeleteResponseDTO(LikesEntity entity) {
        return LikesDeleteResponseDTO.builder()
                .id(entity.getId())
                .voteId(entity.getVoteId())
                .memberId(entity.getMemberId())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}

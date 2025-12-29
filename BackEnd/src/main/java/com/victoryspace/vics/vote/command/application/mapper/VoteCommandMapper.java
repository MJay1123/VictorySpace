package com.victoryspace.vics.vote.command.application.mapper;

import com.victoryspace.vics.vote.command.application.dto.VoteCommandDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteChallengeResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteCreateResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteDeleteResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteUpdateResponseDTO;
import com.victoryspace.vics.vote.command.domain.aggregate.VoteEntity;
import org.springframework.stereotype.Component;

@Component
public class VoteCommandMapper {
    public VoteCreateResponseDTO toCreateResponseDto(VoteEntity entity) {
        return VoteCreateResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .categoryId(entity.getCategoryId())
                .memberId(entity.getMemberId())
                .content(entity.getContent())
                .duration(entity.getDuration())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    public VoteUpdateResponseDTO toUpdateResponseDto(VoteEntity entity) {
        return VoteUpdateResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .categoryId(entity.getCategoryId())
                .content(entity.getContent())
                .duration(entity.getDuration())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    public VoteDeleteResponseDTO toDeleteResponseDto(VoteEntity entity) {
        return VoteDeleteResponseDTO.builder()
                .id(entity.getId())
                .deletedAt(entity.getDeletedAt())
                .build();
    }
    public VoteChallengeResponseDTO toChallengeResponseDto(VoteEntity entity) {
        return VoteChallengeResponseDTO.builder()
                .id(entity.getId())
                .challengerId(entity.getChallengerId())
                .challengerContent(entity.getChallengerContent())
                .updatedAt(entity.getUpdatedAt())
                .endedAt(entity.getEndedAt())
                .build();
    }

}

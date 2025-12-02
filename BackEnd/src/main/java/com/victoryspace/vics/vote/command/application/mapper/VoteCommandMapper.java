package com.victoryspace.vics.vote.command.application.mapper;

import com.victoryspace.vics.vote.command.application.dto.VoteCommandDTO;
import com.victoryspace.vics.vote.command.domain.aggregate.VoteEntity;
import org.springframework.stereotype.Component;

@Component
public class VoteCommandMapper {
    public VoteCommandDTO toDto(VoteEntity entity) {
        VoteCommandDTO dto = new VoteCommandDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCategoryId(entity.getCategoryId());
        dto.setMemberId(entity.getMemberId());
        dto.setContent(entity.getContent());
        dto.setChallengerId(entity.getChallengerId());
        dto.setChallengerContent(entity.getChallengerContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setDuration(entity.getDuration());
        dto.setEndedAt(entity.getEndedAt());
        return dto;
    }
}

package com.victoryspace.vics.likes.command.application.mapper;

import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;
import com.victoryspace.vics.likes.command.domain.aggregate.LikesEntity;
import org.springframework.stereotype.Component;

@Component
public class LikesCommandMapper {
    public LikesCommandDTO toDto(LikesEntity entity) {
        LikesCommandDTO dto = new LikesCommandDTO();
        dto.setId(entity.getId());
        dto.setVoteId(entity.getVoteId());
        dto.setMemberId(entity.getMemberId());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}

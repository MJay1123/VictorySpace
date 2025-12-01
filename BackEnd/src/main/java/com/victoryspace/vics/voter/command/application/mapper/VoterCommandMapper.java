package com.victoryspace.vics.voter.command.application.mapper;

import com.victoryspace.vics.voter.command.application.dto.VoterCommandDTO;
import com.victoryspace.vics.voter.command.domain.aggregate.VoterEntity;
import org.springframework.stereotype.Component;

@Component
public class VoterCommandMapper {
    public VoterCommandDTO toDto(VoterEntity voterEntity) {
        VoterCommandDTO voterCommandDTO = new VoterCommandDTO();
        voterCommandDTO.setId(voterEntity.getId());
        voterCommandDTO.setVoteId(voterEntity.getVoteId());
        voterCommandDTO.setMemberId(voterEntity.getMemberId());
        voterCommandDTO.setContent(voterEntity.getContent());
        return voterCommandDTO;
    }
}

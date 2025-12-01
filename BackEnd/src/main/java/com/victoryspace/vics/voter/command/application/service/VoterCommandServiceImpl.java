package com.victoryspace.vics.voter.command.application.service;

import com.victoryspace.vics.voter.command.application.dto.VoterCommandDTO;
import com.victoryspace.vics.voter.command.application.mapper.VoterCommandMapper;
import com.victoryspace.vics.voter.command.domain.aggregate.VoterEntity;
import com.victoryspace.vics.voter.command.domain.repository.VoterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoterCommandServiceImpl implements VoterCommandService {
    private final VoterRepository voterRepository;
    private final VoterCommandMapper voterCommandMapper;

    @Override
    public VoterCommandDTO createVoter(VoterCommandDTO voterCommandDTO) {
        int voteId =  voterCommandDTO.getVoteId();
        int memberId =  voterCommandDTO.getMemberId();
        if(voterRepository.existsByVoteIdAndMemberId(voteId, memberId)){
            throw new RuntimeException("voter already exists");
        }
        VoterEntity voterEntity = new VoterEntity();
        voterEntity.setVoteId(voterCommandDTO.getVoteId());
        voterEntity.setMemberId(voterCommandDTO.getMemberId());
        voterEntity.setContent(voterCommandDTO.getContent());
        VoterEntity createdVoterEntity = voterRepository.save(voterEntity);
        return voterCommandMapper.toDto(createdVoterEntity);
    }

    @Override
    public VoterCommandDTO deleteVoter(Integer id) {
        VoterEntity voterEntity = voterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Voter not found"));
        voterRepository.delete(voterEntity);
        return voterCommandMapper.toDto(voterEntity);
    }
}

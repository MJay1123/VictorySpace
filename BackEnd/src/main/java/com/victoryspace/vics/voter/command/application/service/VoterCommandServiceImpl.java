package com.victoryspace.vics.voter.command.application.service;

import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.voter.command.application.dto.VoterCommandDTO;
import com.victoryspace.vics.voter.command.application.dto.request.VoterCreateRequestDTO;
import com.victoryspace.vics.voter.command.application.dto.response.VoterCreateResponseDTO;
import com.victoryspace.vics.voter.command.application.dto.response.VoterDeleteResponseDTO;
import com.victoryspace.vics.voter.command.application.mapper.VoterCommandMapper;
import com.victoryspace.vics.voter.command.domain.aggregate.VoterEntity;
import com.victoryspace.vics.voter.command.domain.repository.VoterRepository;
import com.victoryspace.vics.voter.exception.VoterException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoterCommandServiceImpl implements VoterCommandService {
    private final VoterRepository voterRepository;
    private final VoterCommandMapper voterCommandMapper;

    @Override
    public VoterCreateResponseDTO createVoter(VoterCreateRequestDTO requestDTO) {
        int voteId = requestDTO.getVoteId();
        int memberId = requestDTO.getMemberId();
        if(voterRepository.existsByVoteIdAndMemberId(voteId, memberId)){
            throw new VoterException(ErrorCode.ALREADY_VOTED);
        }
        VoterEntity voterEntity = VoterEntity.create(
                voteId,
                memberId,
                requestDTO.getContent()
        );
        voterRepository.save(voterEntity);
        return voterCommandMapper.toCreateResponseDTO(voterEntity);
    }

    @Override
    public VoterDeleteResponseDTO deleteVoter(Integer id) {
        VoterEntity voterEntity = voterRepository.findById(id)
                .orElseThrow(() -> new VoterException(ErrorCode.VOTER_NOT_FOUND));
        voterRepository.delete(voterEntity);
        return voterCommandMapper.toDeleteResponseDTO(voterEntity);
    }
}

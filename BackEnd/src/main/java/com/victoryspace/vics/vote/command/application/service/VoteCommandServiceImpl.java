package com.victoryspace.vics.vote.command.application.service;

import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.vote.command.application.dto.request.VoteChallengeRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.request.VoteCreateRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.request.VoteUpdateRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteChallengeResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteCreateResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteDeleteResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteUpdateResponseDTO;
import com.victoryspace.vics.vote.command.application.mapper.VoteCommandMapper;
import com.victoryspace.vics.vote.command.domain.aggregate.VoteEntity;
import com.victoryspace.vics.vote.command.domain.repository.VoteRepository;
import com.victoryspace.vics.vote.exception.VoteException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteCommandServiceImpl implements VoteCommandService {
    private final VoteRepository voteRepository;
    private final VoteCommandMapper voteCommandMapper;

    @Override
    public VoteCreateResponseDTO createVote(VoteCreateRequestDTO requestDTO) {
        VoteEntity voteEntity = VoteEntity.create(
                requestDTO.getTitle(),
                requestDTO.getCategoryId(),
                requestDTO.getContent(),
                requestDTO.getMemberId(),
                requestDTO.getDuration()
        );
        return voteCommandMapper.toCreateResponseDto(voteRepository.save(voteEntity));
    }

    @Override
    public VoteUpdateResponseDTO updateVote(Integer id, VoteUpdateRequestDTO requestDTO) {
        VoteEntity voteEntity = voteRepository.findById(id)
                .orElseThrow(() -> new VoteException(ErrorCode.VOTE_NOT_FOUND));
        voteEntity.update(
                requestDTO.getTitle(),
                requestDTO.getCategoryId(),
                requestDTO.getContent(),
                requestDTO.getDuration()
        );
        return voteCommandMapper.toUpdateResponseDto(voteRepository.save(voteEntity));
    }

    @Override
    public VoteDeleteResponseDTO deleteVote(Integer id) {
        VoteEntity voteEntity = voteRepository.findById(id)
                .orElseThrow(() -> new VoteException(ErrorCode.VOTE_NOT_FOUND));
        voteEntity.delete();
        return voteCommandMapper.toDeleteResponseDto(voteRepository.save(voteEntity));
    }

    @Override
    public VoteChallengeResponseDTO challengeVote(Integer voteId, VoteChallengeRequestDTO requestDTO) {
        VoteEntity voteEntity = voteRepository.findById(voteId)
                .orElseThrow(() -> new VoteException(ErrorCode.VOTE_NOT_FOUND));
        if(voteEntity.getChallengerId() != null){
            throw new VoteException(ErrorCode.VOTE_ALREADY_CHALLENGED);
        }
        if(voteEntity.getMemberId() == requestDTO.getChallengerId()){
            throw new VoteException(ErrorCode.VOTE_SELF_CHALLENGED);
        }
        voteEntity.challenge(
                requestDTO.getChallengerId(),
                requestDTO.getChallengerContent()
        );
        return voteCommandMapper.toChallengeResponseDto(voteRepository.save(voteEntity));
    }
}

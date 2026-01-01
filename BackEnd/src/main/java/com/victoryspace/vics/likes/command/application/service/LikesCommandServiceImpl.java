package com.victoryspace.vics.likes.command.application.service;

import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;
import com.victoryspace.vics.likes.command.application.dto.request.LikesCreateRequestDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesCreateResponseDTO;
import com.victoryspace.vics.likes.command.application.dto.response.LikesDeleteResponseDTO;
import com.victoryspace.vics.likes.command.application.mapper.LikesCommandMapper;
import com.victoryspace.vics.likes.command.domain.aggregate.LikesEntity;
import com.victoryspace.vics.likes.command.domain.repository.LikesRepository;
import com.victoryspace.vics.likes.exception.LikesException;
import com.victoryspace.vics.vote.exception.VoteException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikesCommandServiceImpl implements LikesCommandService {
    private final LikesRepository repository;
    private final LikesCommandMapper mapper;

    @Override
    public LikesCreateResponseDTO createLikes(LikesCreateRequestDTO requestDTO) {
        int voteId = requestDTO.getVoteId();
        int memberId = requestDTO.getMemberId();
        if(repository.existsByVoteIdAndMemberId(voteId, memberId)){
            throw new LikesException(ErrorCode.LIKES_ALREADY_EXISTS);
        }
        LikesEntity entity = LikesEntity.create(
                voteId,
                memberId
        );
        repository.save(entity);
        return mapper.toCreateResponseDTO(entity);
    }

    @Override
    public LikesDeleteResponseDTO deleteLikes(int id) {
        LikesEntity entity = repository.findById(id)
                .orElseThrow(() -> new VoteException(ErrorCode.LIKES_NOT_FOUMD));
        repository.delete(entity);
        return mapper.toDeleteResponseDTO(entity);
    }
}

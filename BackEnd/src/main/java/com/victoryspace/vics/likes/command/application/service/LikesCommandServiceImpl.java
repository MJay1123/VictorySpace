package com.victoryspace.vics.likes.command.application.service;

import com.victoryspace.vics.likes.command.application.dto.LikesCommandDTO;
import com.victoryspace.vics.likes.command.application.mapper.LikesCommandMapper;
import com.victoryspace.vics.likes.command.domain.aggregate.LikesEntity;
import com.victoryspace.vics.likes.command.domain.repository.LikesRepository;
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
    public LikesCommandDTO createLike(LikesCommandDTO dto) {
        LikesEntity entity = new LikesEntity();
        entity.setVoteId(dto.getVoteId());
        entity.setMemberId(dto.getMemberId());
        entity.setCreatedAt(LocalDateTime.now());
        LikesEntity createdEntity = repository.save(entity);
        return mapper.toDto(createdEntity);
    }

    @Override
    public LikesCommandDTO deleteLike(int id) {
        LikesEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Like not found"));
        repository.delete(entity);
        return mapper.toDto(entity);
    }
}

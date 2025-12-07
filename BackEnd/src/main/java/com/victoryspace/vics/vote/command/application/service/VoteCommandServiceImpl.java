package com.victoryspace.vics.vote.command.application.service;

import com.victoryspace.vics.vote.command.application.dto.VoteCommandDTO;
import com.victoryspace.vics.vote.command.application.mapper.VoteCommandMapper;
import com.victoryspace.vics.vote.command.domain.aggregate.VoteEntity;
import com.victoryspace.vics.vote.command.domain.repository.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VoteCommandServiceImpl implements VoteCommandService {
    private final VoteRepository voteRepository;
    private final VoteCommandMapper voteCommandMapper;

    @Override
    public VoteCommandDTO createVote(VoteCommandDTO voteCommandDTO) {
        VoteEntity voteEntity = new VoteEntity();
        voteEntity.setTitle(voteCommandDTO.getTitle());
        voteEntity.setCategoryId(voteCommandDTO.getCategoryId());
        voteEntity.setContent(voteCommandDTO.getContent());
        voteEntity.setMemberId(voteCommandDTO.getMemberId());
        voteEntity.setCreatedAt(LocalDateTime.now());
        voteEntity.setUpdatedAt(LocalDateTime.now());
        voteEntity.setDuration(voteCommandDTO.getDuration());
        VoteEntity createdVoteEntity = voteRepository.save(voteEntity);
        return voteCommandMapper.toDto(createdVoteEntity);
    }

    @Override
    public VoteCommandDTO updateVote(Integer id, VoteCommandDTO voteCommandDTO) {
        VoteEntity voteEntity = voteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vote not found"));
        voteEntity.setTitle(voteCommandDTO.getTitle());
        voteEntity.setCategoryId(voteCommandDTO.getCategoryId());
        voteEntity.setContent(voteCommandDTO.getContent());
        voteEntity.setUpdatedAt(LocalDateTime.now());
        voteEntity.setDuration(voteCommandDTO.getDuration());
        VoteEntity updatedVoteEntity = voteRepository.save(voteEntity);
        return voteCommandMapper.toDto(updatedVoteEntity);
    }

    @Override
    public VoteCommandDTO deleteVote(Integer id) {
        VoteEntity voteEntity = voteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vote not found"));
        voteEntity.setUpdatedAt(LocalDateTime.now());
        voteEntity.setDeletedAt(LocalDateTime.now());
        VoteEntity deletedVoteEntity = voteRepository.save(voteEntity);
        return voteCommandMapper.toDto(deletedVoteEntity);
    }

    @Override
    public VoteCommandDTO challengeVote(Integer voteId, VoteCommandDTO voteCommandDTO) {
        VoteEntity voteEntity = voteRepository.findById(voteId)
                .orElseThrow(() -> new EntityNotFoundException("Vote not found"));
        Integer challengerId = voteCommandDTO.getChallengerId();
        if(voteEntity.getMemberId() == challengerId){
            throw new RuntimeException("cannot challenge myself");
        }
        if(voteEntity.getChallengerId() != null){
            throw new RuntimeException("already challenged");
        }
        String challengerContent = voteCommandDTO.getChallengerContent();
        voteEntity.setChallengerId(challengerId);
        voteEntity.setChallengerContent(challengerContent);
        voteEntity.setUpdatedAt(LocalDateTime.now());

        String duration = voteEntity.getDuration(); // 예: "3h", "5d"
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endedAt;
        if (duration.endsWith("h")) {
            int hours = Integer.parseInt(duration.replace("h", ""));
            endedAt = now.plusHours(hours);
        } else if (duration.endsWith("d")) {
            int days = Integer.parseInt(duration.replace("d", ""));
            endedAt = now.plusDays(days);
        } else {
            throw new IllegalArgumentException("Invalid duration format: " + duration);
        }
        voteEntity.setEndedAt(endedAt);
        VoteEntity updatedVoteEntity = voteRepository.save(voteEntity);
        return voteCommandMapper.toDto(updatedVoteEntity);
    }
}

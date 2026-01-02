package com.victoryspace.vics.likes.query.service;

import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;
import com.victoryspace.vics.likes.query.mapper.LikesQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesQueryServiceImpl implements LikesQueryService {
    private final LikesQueryMapper mapper;

    @Override
    public List<LikesListDTO> findByVoteId(int voteId) {
        return mapper.findByVoteId(voteId);
    }
}

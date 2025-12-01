package com.victoryspace.vics.likes.query.service;

import com.victoryspace.vics.likes.query.dto.LikesQueryDTO;
import com.victoryspace.vics.likes.query.mapper.LikesQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesQueryServiceImpl implements LikesQueryService {
    private final LikesQueryMapper mapper;

    @Override
    public List<LikesQueryDTO> findAll() {
        List<LikesQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public LikesQueryDTO findById(int id) {
        LikesQueryDTO dto = mapper.findById(id);
        return dto;
    }

    @Override
    public List<LikesQueryDTO> findByVoteId(int voteId) {
        List<LikesQueryDTO> dtoList = mapper.findByVoteId(voteId);
        return dtoList;
    }

    @Override
    public List<LikesQueryDTO> findByMemberId(int memberId) {
        List<LikesQueryDTO> dtoList = mapper.findByMemberId(memberId);
        return dtoList;
    }
}

package com.victoryspace.vics.voter.query.service;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.response.VoterListDTO;
import com.victoryspace.vics.voter.query.mapper.VoterQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoterQueryServiceImpl implements VoterQueryService {
    private final VoterQueryMapper mapper;

    @Override
    public List<VoterListDTO> findByVoteId(int voteId) {
        return mapper.findByVoteId(voteId);
    }

}

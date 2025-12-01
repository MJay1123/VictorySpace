package com.victoryspace.vics.voter.query.service;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.VoterSearchDTO;
import com.victoryspace.vics.voter.query.mapper.VoterQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoterQueryServiceImpl implements VoterQueryService {
    private final VoterQueryMapper mapper;

    @Override
    public List<VoterQueryDTO> findAll(){
        List<VoterQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public VoterQueryDTO findById(int id) {
        VoterQueryDTO memberQueryDTO = mapper.findById(id);
        return memberQueryDTO;
    }

    @Override
    public List<VoterQueryDTO> findByVoteId(int voteId) {
        List<VoterQueryDTO> dtoList = mapper.findByVoteId(voteId);
        return dtoList;
    }

    @Override
    public List<VoterQueryDTO> findByMemberId(int memberId) {
        List<VoterQueryDTO> dtoList = mapper.findByMemberId(memberId);
        return dtoList;
    }
}

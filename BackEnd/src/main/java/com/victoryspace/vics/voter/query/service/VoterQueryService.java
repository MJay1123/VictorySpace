package com.victoryspace.vics.voter.query.service;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.VoterSearchDTO;

import java.util.List;

public interface VoterQueryService {

    List<VoterQueryDTO> findAll();

    VoterQueryDTO findById(int id);

    List<VoterQueryDTO> findByVoteId(int voteId);

    List<VoterQueryDTO> findByMemberId(int memberId);

    VoterQueryDTO findByVoteAndMemberId(int voteId, int memberId);
}

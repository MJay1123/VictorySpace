package com.victoryspace.vics.voter.query.service;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.response.VoterListDTO;

import java.util.List;

public interface VoterQueryService {

    List<VoterListDTO> findByVoteId(int voteId);

}

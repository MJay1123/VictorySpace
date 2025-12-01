package com.victoryspace.vics.vote.query.service;

import com.victoryspace.vics.vote.query.dto.VoteQueryDTO;
import com.victoryspace.vics.vote.query.dto.VoteSearchDTO;

import java.util.List;

public interface VoteQueryService {

    List<VoteQueryDTO> findAll();

    VoteQueryDTO findById(int id);

    List<VoteQueryDTO> search(VoteSearchDTO voteSearchDTO);

    List<VoteQueryDTO> findByMemberId(int memberId);

    List<VoteQueryDTO> findByChallengerId(int challengerId);
}

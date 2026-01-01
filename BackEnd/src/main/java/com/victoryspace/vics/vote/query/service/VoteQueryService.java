package com.victoryspace.vics.vote.query.service;

import com.victoryspace.vics.vote.query.dto.response.VoteQueryDetailResponseDTO;
import com.victoryspace.vics.vote.query.dto.request.VoteSearchDTO;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryListResponseDTO;

import java.util.List;

public interface VoteQueryService {

    List<VoteQueryListResponseDTO> findAll();

    VoteQueryDetailResponseDTO findById(int id);

    List<VoteQueryListResponseDTO> search(VoteSearchDTO voteSearchDTO);

    List<VoteQueryListResponseDTO> findByMemberId(int memberId);

    List<VoteQueryListResponseDTO> findByChallengerId(int challengerId);
}

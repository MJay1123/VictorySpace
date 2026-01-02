package com.victoryspace.vics.likes.query.service;

import com.victoryspace.vics.likes.query.dto.LikesQueryDTO;
import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;

import java.util.List;

public interface LikesQueryService {
    List<LikesQueryDTO> findAll();

    LikesQueryDTO findById(int id);

    List<LikesListDTO> findByVoteId(int voteId);

    List<LikesQueryDTO> findByMemberId(int memberId);

    LikesQueryDTO findByVoteIdAndMemberId(int voteId, int memberId);
}

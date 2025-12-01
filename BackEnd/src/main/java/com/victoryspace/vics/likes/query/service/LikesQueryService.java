package com.victoryspace.vics.likes.query.service;

import com.victoryspace.vics.likes.query.dto.LikesQueryDTO;

import java.util.List;

public interface LikesQueryService {
    List<LikesQueryDTO> findAll();

    LikesQueryDTO findById(int id);

    List<LikesQueryDTO> findByVoteId(int voteId);

    List<LikesQueryDTO> findByMemberId(int memberId);
}

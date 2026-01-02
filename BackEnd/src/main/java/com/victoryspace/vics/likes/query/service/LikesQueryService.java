package com.victoryspace.vics.likes.query.service;

import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;

import java.util.List;

public interface LikesQueryService {

    List<LikesListDTO> findByVoteId(int voteId);

}

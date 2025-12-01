package com.victoryspace.vics.comment.query.service;

import com.victoryspace.vics.comment.query.dto.CommentQueryDTO;

import java.util.List;

public interface CommentQueryService {

    List<CommentQueryDTO> findAll();

    CommentQueryDTO findById(int id);

    List<CommentQueryDTO> findByVoteId(int voteId);

    List<CommentQueryDTO> findByMemberId(int memberId);
}

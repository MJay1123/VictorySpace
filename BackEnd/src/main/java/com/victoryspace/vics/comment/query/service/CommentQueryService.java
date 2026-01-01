package com.victoryspace.vics.comment.query.service;

import com.victoryspace.vics.comment.query.dto.CommentQueryDTO;
import com.victoryspace.vics.comment.query.dto.response.VoteCommentResponseDTO;

import java.util.List;

public interface CommentQueryService {

    List<CommentQueryDTO> findAll();

    CommentQueryDTO findById(int id);

    List<VoteCommentResponseDTO> findByVoteId(int voteId);

    List<CommentQueryDTO> findByMemberId(int memberId);
}

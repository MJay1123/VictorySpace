package com.victoryspace.vics.comment.query.service;

import com.victoryspace.vics.comment.query.dto.response.VoteCommentResponseDTO;

import java.util.List;

public interface CommentQueryService {

    List<VoteCommentResponseDTO> findByVoteId(int voteId);
}

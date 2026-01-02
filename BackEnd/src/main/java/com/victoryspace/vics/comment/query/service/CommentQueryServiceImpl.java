package com.victoryspace.vics.comment.query.service;

import com.victoryspace.vics.comment.query.dto.response.VoteCommentResponseDTO;
import com.victoryspace.vics.comment.query.mapper.CommentQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentQueryServiceImpl implements CommentQueryService {
    private final CommentQueryMapper mapper;

    @Override
    public List<VoteCommentResponseDTO> findByVoteId(int voteId) {
        return mapper.findByVoteId(voteId);
    }
}

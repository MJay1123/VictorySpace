package com.victoryspace.vics.comment.query.mapper;

import com.victoryspace.vics.comment.query.dto.response.VoteCommentResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentQueryMapper {

    List<VoteCommentResponseDTO> findByVoteId(int voteId);
}

package com.victoryspace.vics.comment.query.mapper;

import com.victoryspace.vics.comment.query.dto.CommentQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentQueryMapper {
    List<CommentQueryDTO> findAll();

    CommentQueryDTO findById(int id);

    List<CommentQueryDTO> findByVoteId(int voteId);

    List<CommentQueryDTO> findByMemberId(int memberId);
}

package com.victoryspace.vics.likes.query.mapper;

import com.victoryspace.vics.likes.query.dto.LikesQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikesQueryMapper {
    List<LikesQueryDTO> findAll();

    LikesQueryDTO findById(int id);

    List<LikesQueryDTO> findByVoteId(int voteId);

    List<LikesQueryDTO> findByMemberId(int memberId);
}

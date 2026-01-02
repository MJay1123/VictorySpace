package com.victoryspace.vics.likes.query.mapper;

import com.victoryspace.vics.likes.query.dto.LikesQueryDTO;
import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikesQueryMapper {
    List<LikesQueryDTO> findAll();

    LikesQueryDTO findById(int id);

    List<LikesListDTO> findByVoteId(int voteId);

    List<LikesQueryDTO> findByMemberId(int memberId);

    LikesQueryDTO findByVoteIdAndMemberId(int voteId, int memberId);
}

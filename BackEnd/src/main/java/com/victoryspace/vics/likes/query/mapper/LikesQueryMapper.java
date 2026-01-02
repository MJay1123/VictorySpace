package com.victoryspace.vics.likes.query.mapper;

import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikesQueryMapper {

    List<LikesListDTO> findByVoteId(int voteId);

}

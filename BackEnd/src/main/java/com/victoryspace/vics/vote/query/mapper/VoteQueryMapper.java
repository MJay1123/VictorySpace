package com.victoryspace.vics.vote.query.mapper;

import com.victoryspace.vics.vote.query.dto.response.VoteQueryDetailResponseDTO;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryListResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoteQueryMapper {
    List<VoteQueryListResponseDTO> findAll();

    VoteQueryDetailResponseDTO findById(int id);

    List<VoteQueryListResponseDTO> findByMemberId(int memberId);

    List<VoteQueryListResponseDTO> findByChallengerId(int challengerId);

    List<VoteQueryListResponseDTO> search(String title, Integer categoryId, String nickname, String content);
}

package com.victoryspace.vics.vote.query.mapper;

import com.victoryspace.vics.vote.query.dto.VoteQueryDTO;
import com.victoryspace.vics.vote.query.dto.VoteSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoteQueryMapper {
    List<VoteQueryDTO> findAll();

    VoteQueryDTO findById(int id);

    List<VoteQueryDTO> findByMemberId(int memberId);

    List<VoteQueryDTO> findByChallengerId(int challengerId);

    List<VoteQueryDTO> search(String title, Integer categoryId, String nickname, String content);
}

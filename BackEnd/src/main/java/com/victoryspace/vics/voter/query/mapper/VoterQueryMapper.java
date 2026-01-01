package com.victoryspace.vics.voter.query.mapper;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.response.VoterListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VoterQueryMapper {
    List<VoterQueryDTO> findAll();

    VoterQueryDTO findById(int id);

    List<VoterListDTO> findByVoteId(int voteId);

    List<VoterQueryDTO> findByMemberId(int memberId);

    VoterQueryDTO findByVoteAndMemberId(int voteId, int memberId);
}

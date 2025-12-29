package com.victoryspace.vics.vote.query.service;

import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.vote.exception.VoteException;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryCountDTO;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryDetailResponseDTO;
import com.victoryspace.vics.vote.query.dto.request.VoteSearchDTO;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryListResponseDTO;
import com.victoryspace.vics.vote.query.mapper.VoteQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteQueryServiceImpl implements VoteQueryService {
    private final VoteQueryMapper mapper;

    @Override
    public List<VoteQueryListResponseDTO> findAll(){
        List<VoteQueryListResponseDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public VoteQueryDetailResponseDTO findById(int id) {
        VoteQueryDetailResponseDTO detailDTO = mapper.findById(id);
        if(detailDTO == null) {
            throw new VoteException(ErrorCode.VOTE_NOT_FOUND);
        }

        VoteQueryCountDTO countDTO = mapper.findVoteCounts(id);
        if(countDTO == null) {
            detailDTO.setHomeCount(0);
            detailDTO.setAwayCount(0);
            detailDTO.setNeutralCount(0);
        } else {
            detailDTO.setHomeCount(countDTO.getHomeCount());
            detailDTO.setAwayCount(countDTO.getAwayCount());
            detailDTO.setNeutralCount(countDTO.getNeutralCount());
        }

        return detailDTO;
    }

    @Override
    public List<VoteQueryListResponseDTO> search(VoteSearchDTO voteSearchDTO) {
        String title = voteSearchDTO.getTitle();
        Integer categoryId = voteSearchDTO.getCategoryId();
        String nickname = voteSearchDTO.getNickname();
        String content = voteSearchDTO.getContent();
        List<VoteQueryListResponseDTO> dtoList = mapper.search(title, categoryId, nickname, content);
        return dtoList;
    }

    @Override
    public List<VoteQueryListResponseDTO> findByMemberId(int memberId) {
        List<VoteQueryListResponseDTO> dtoList = mapper.findByMemberId(memberId);
        return dtoList;
    }

    @Override
    public List<VoteQueryListResponseDTO> findByChallengerId(int challengerId) {
        List<VoteQueryListResponseDTO> dtoList = mapper.findByChallengerId(challengerId);
        return dtoList;
    }
}

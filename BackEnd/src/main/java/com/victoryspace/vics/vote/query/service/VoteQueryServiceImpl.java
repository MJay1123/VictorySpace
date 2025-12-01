package com.victoryspace.vics.vote.query.service;

import com.victoryspace.vics.vote.query.dto.VoteQueryDTO;
import com.victoryspace.vics.vote.query.dto.VoteSearchDTO;
import com.victoryspace.vics.vote.query.mapper.VoteQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteQueryServiceImpl implements VoteQueryService {
    private final VoteQueryMapper mapper;

    @Override
    public List<VoteQueryDTO> findAll(){
        List<VoteQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public VoteQueryDTO findById(int id) {
        VoteQueryDTO memberQueryDTO = mapper.findById(id);
        return memberQueryDTO;
    }

    @Override
    public List<VoteQueryDTO> search(VoteSearchDTO voteSearchDTO) {
        String title = voteSearchDTO.getTitle();
        Integer categoryId = voteSearchDTO.getCategoryId();
        String nickname = voteSearchDTO.getNickname();
        String content = voteSearchDTO.getContent();
        List<VoteQueryDTO> dtoList = mapper.search(title, categoryId, nickname, content);
        return dtoList;
    }

    @Override
    public List<VoteQueryDTO> findByMemberId(int memberId) {
        List<VoteQueryDTO> dtoList = mapper.findByMemberId(memberId);
        return dtoList;
    }

    @Override
    public List<VoteQueryDTO> findByChallengerId(int challengerId) {
        List<VoteQueryDTO> dtoList = mapper.findByChallengerId(challengerId);
        return dtoList;
    }
}

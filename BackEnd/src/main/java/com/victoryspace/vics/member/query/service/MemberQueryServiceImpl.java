package com.victoryspace.vics.member.query.service;

import com.victoryspace.vics.member.query.dto.MemberInfoDTO;
import com.victoryspace.vics.member.query.dto.MemberPointRankListDTO;
import com.victoryspace.vics.member.query.dto.MemberQueryDTO;
import com.victoryspace.vics.member.query.mapper.MemberQueryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService {
    private final MemberQueryMapper mapper;

    @Override
    public List<MemberInfoDTO> findAll(){
        List<MemberInfoDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public MemberInfoDTO findById(int id) {
        MemberInfoDTO memberQueryDTO = mapper.findById(id);
        return memberQueryDTO;
    }

    @Override
    public List<MemberInfoDTO> findByNickname(String nickname) {
        List<MemberInfoDTO> dtoList = mapper.findByNickname(nickname);
        return dtoList;
    }

    @Override
    public MemberInfoDTO findByEmail(String email) {
        MemberInfoDTO dto = mapper.findByEmail(email);
        return dto;
    }

    @Override
    public List<MemberPointRankListDTO> orderRank() {
        return mapper.orderRank();
    }

    @Override
    public List<MemberInfoDTO> order(String orderBy, String direction) {
        if(orderBy.equals("point")) {
            List<MemberInfoDTO> dtoList = mapper.orderByPoint(direction);
            return dtoList;
        }
        return null;
    }
}

package com.victoryspace.vics.member.query.service;

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
    public List<MemberQueryDTO> findAll(){
        List<MemberQueryDTO> dtoList = mapper.findAll();
        return dtoList;
    }

    @Override
    public MemberQueryDTO findById(int id) {
        MemberQueryDTO memberQueryDTO = mapper.findById(id);
        return memberQueryDTO;
    }

    @Override
    public List<MemberQueryDTO> findByNickname(String nickname) {
        List<MemberQueryDTO> dtoList = mapper.findByNickname(nickname);
        return dtoList;
    }

    @Override
    public MemberQueryDTO findByEmail(String email) {
        MemberQueryDTO dto = mapper.findByEmail(email);
        return dto;
    }

    @Override
    public List<MemberQueryDTO> order(String orderBy, String direction) {
        if(orderBy.equals("point")) {
            List<MemberQueryDTO> dtoList = mapper.orderByPoint(direction);
            return dtoList;
        }
        return null;
    }
}

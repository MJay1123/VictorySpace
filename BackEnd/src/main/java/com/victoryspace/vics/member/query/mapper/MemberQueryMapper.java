package com.victoryspace.vics.member.query.mapper;

import com.victoryspace.vics.member.query.dto.MemberInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberQueryMapper {
    List<MemberInfoDTO> findAll();

    MemberInfoDTO findById(int id);

    List<MemberInfoDTO> findByNickname(String nickname);

    MemberInfoDTO findByEmail(String email);

    List<MemberInfoDTO> orderByPoint(String direction);
}

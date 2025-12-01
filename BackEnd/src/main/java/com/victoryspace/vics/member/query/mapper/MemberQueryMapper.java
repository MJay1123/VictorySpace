package com.victoryspace.vics.member.query.mapper;

import com.victoryspace.vics.member.query.dto.MemberQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberQueryMapper {
    List<MemberQueryDTO> findAll();

    MemberQueryDTO findById(int id);

    List<MemberQueryDTO> findByNickname(String nickname);

    List<MemberQueryDTO> orderByPoint(String direction);
}

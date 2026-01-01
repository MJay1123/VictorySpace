package com.victoryspace.vics.member.query.service;

import com.victoryspace.vics.member.query.dto.MemberInfoDTO;
import com.victoryspace.vics.member.query.dto.MemberQueryDTO;

import java.util.List;

public interface MemberQueryService {

    List<MemberInfoDTO> findAll();

    MemberInfoDTO findById(int id);

    List<MemberInfoDTO> findByNickname(String nickname);

    List<MemberInfoDTO> order(String orderBy, String direction);

    MemberInfoDTO findByEmail(String email);
}

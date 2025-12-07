package com.victoryspace.vics.member.query.service;

import com.victoryspace.vics.member.query.dto.MemberQueryDTO;

import java.util.List;

public interface MemberQueryService {

    List<MemberQueryDTO> findAll();

    MemberQueryDTO findById(int id);

    List<MemberQueryDTO> findByNickname(String nickname);

    List<MemberQueryDTO> order(String orderBy, String direction);

    MemberQueryDTO findByEmail(String email);
}

package com.victoryspace.vics.member.command.application.service;

import com.victoryspace.vics.member.command.application.dto.MemberCommandDTO;

public interface MemberCommandService {

    MemberCommandDTO updateMember(Integer id, MemberCommandDTO memberCommandDTO);

    MemberCommandDTO deleteMember(Integer id);
}

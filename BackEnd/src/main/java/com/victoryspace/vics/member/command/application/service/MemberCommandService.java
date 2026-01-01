package com.victoryspace.vics.member.command.application.service;

import com.victoryspace.vics.member.command.application.dto.MemberCommandDTO;
import com.victoryspace.vics.member.command.application.dto.request.MemberUpdateRequestDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberDeleteResponseDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberUpdateReponseDTO;

public interface MemberCommandService {

    MemberUpdateReponseDTO updateMember(Integer id, MemberUpdateRequestDTO requestDTO);

    MemberDeleteResponseDTO deleteMember(Integer id);
}

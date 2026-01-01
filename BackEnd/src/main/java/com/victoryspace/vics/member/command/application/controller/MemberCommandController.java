package com.victoryspace.vics.member.command.application.controller;

import com.victoryspace.vics.member.command.application.dto.request.MemberUpdateRequestDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberDeleteResponseDTO;
import com.victoryspace.vics.member.command.application.dto.response.MemberUpdateResponseDTO;
import com.victoryspace.vics.member.command.application.service.MemberCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberCommandController {
    private final MemberCommandService memberCommandService;

    @Autowired
    public MemberCommandController(MemberCommandService memberCommandService) {
        this.memberCommandService = memberCommandService;
    }

    @PatchMapping("/{id}")
    public MemberUpdateResponseDTO updateMember(@PathVariable Integer id, @RequestBody MemberUpdateRequestDTO requestDTO) {
        return memberCommandService.updateMember(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public MemberDeleteResponseDTO deleteMember(@PathVariable Integer id) {
        return memberCommandService.deleteMember(id);
    }
}

package com.victoryspace.vics.member.command.application.controller;

import com.victoryspace.vics.member.command.application.dto.MemberCommandDTO;
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

    @PostMapping
    public MemberCommandDTO createMember(@RequestBody MemberCommandDTO memberCommandDTO) {
        MemberCommandDTO createdMemberCommandDTO = memberCommandService.createMember(memberCommandDTO);
        return createdMemberCommandDTO;
    }

    @PatchMapping("/{id}")
    public MemberCommandDTO updateMember(@PathVariable Integer id, @RequestBody MemberCommandDTO memberCommandDTO) {
        MemberCommandDTO updatedMemberCommandDTO = memberCommandService.updateMember(id, memberCommandDTO);
        return updatedMemberCommandDTO;
    }

    @DeleteMapping("/{id}")
    public MemberCommandDTO deleteMember(@PathVariable Integer id) {
        MemberCommandDTO deletedMemberCommandDTO = memberCommandService.deleteMember(id);
        return deletedMemberCommandDTO;
    }

    @PostMapping("/login")
    public MemberCommandDTO login(@RequestBody MemberCommandDTO memberCommandDTO) {
        String email = memberCommandDTO.getEmail();
        String password = memberCommandDTO.getPassword();
        MemberCommandDTO loginDTO = memberCommandService.login(email, password);
        return loginDTO;
    }

}

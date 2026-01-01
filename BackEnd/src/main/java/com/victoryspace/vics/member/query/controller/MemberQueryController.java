package com.victoryspace.vics.member.query.controller;

import com.victoryspace.vics.member.query.dto.MemberInfoDTO;
import com.victoryspace.vics.member.query.dto.MemberQueryDTO;
import com.victoryspace.vics.member.query.service.MemberQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberQueryController {
    private final MemberQueryService memberQueryService;

    @Autowired
    public MemberQueryController(MemberQueryService memberQueryService){
        this.memberQueryService = memberQueryService;
    }
    @GetMapping
    public List<MemberInfoDTO> findAll(){
        return memberQueryService.findAll();
    }
    @GetMapping("/{id}")
    public MemberInfoDTO findById(@PathVariable("id") int id){
        return memberQueryService.findById(id);
    }
    @GetMapping("/nickname")
    public List<MemberInfoDTO> findByNickname(@RequestParam String nickname){
        return memberQueryService.findByNickname(nickname);
    }
    @GetMapping("/email")
    public MemberInfoDTO findByEmail(@RequestParam String email){
        return memberQueryService.findByEmail(email);
    }

    @GetMapping("/order")
    public List<MemberInfoDTO> order(@RequestParam String orderBy, @RequestParam String direction){
        return memberQueryService.order(orderBy, direction);
    }
}

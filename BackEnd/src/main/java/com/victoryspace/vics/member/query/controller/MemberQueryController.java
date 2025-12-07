package com.victoryspace.vics.member.query.controller;

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
    public List<MemberQueryDTO> findAll(){
        List<MemberQueryDTO> list = memberQueryService.findAll();
        return list;
    }
    @GetMapping("/{id}")
    public MemberQueryDTO findById(@PathVariable("id") int id){
        MemberQueryDTO memberQueryDTO = memberQueryService.findById(id);
        return memberQueryDTO;
    }
    @GetMapping("/nickname")
    public List<MemberQueryDTO> findByNickname(@RequestParam String nickname){
        return memberQueryService.findByNickname(nickname);
    }
    @GetMapping("/email")
    public MemberQueryDTO findByEmail(@RequestParam String email){
        return memberQueryService.findByEmail(email);
    }

    @GetMapping("/order")
    public List<MemberQueryDTO> order(@RequestParam String orderBy, @RequestParam String direction){
        return memberQueryService.order(orderBy, direction);
    }
}

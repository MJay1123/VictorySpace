package com.victoryspace.vics.likes.query.controller;

import com.victoryspace.vics.likes.query.dto.LikesQueryDTO;
import com.victoryspace.vics.likes.query.dto.response.LikesListDTO;
import com.victoryspace.vics.likes.query.service.LikesQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikesQueryController {
    private final LikesQueryService queryService;

    @Autowired
    public LikesQueryController(LikesQueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public List<LikesQueryDTO> findAll(){
        List<LikesQueryDTO> dtoList = queryService.findAll();
        return dtoList;
    }
    @GetMapping("/{id}")
    public LikesQueryDTO findById(@PathVariable("id") int id){
        LikesQueryDTO dto = queryService.findById(id);
        return dto;
    }
    @GetMapping("vote/{voteId}")
    public List<LikesListDTO> findByVoteId(@PathVariable("voteId") int voteId){
        return queryService.findByVoteId(voteId);
    }
    @GetMapping("member/{memberId}")
    public List<LikesQueryDTO> findByMemberId(@PathVariable("memberId") int memberId){
        List<LikesQueryDTO> dtoList = queryService.findByMemberId(memberId);
        return dtoList;
    }
    @GetMapping("/member/vote/{voteId}/member/{memberId}")
    public LikesQueryDTO findByVoteIdAndMemberId(@PathVariable("voteId") int voteId, @PathVariable("memberId") int memberId){
        return queryService.findByVoteIdAndMemberId(voteId, memberId);
    }
}

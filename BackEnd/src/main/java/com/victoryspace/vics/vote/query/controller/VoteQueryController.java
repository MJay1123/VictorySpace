package com.victoryspace.vics.vote.query.controller;

import com.victoryspace.vics.vote.query.dto.response.VoteQueryDetailResponseDTO;
import com.victoryspace.vics.vote.query.dto.response.VoteQueryListResponseDTO;
import com.victoryspace.vics.vote.query.dto.request.VoteSearchDTO;
import com.victoryspace.vics.vote.query.service.VoteQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
public class VoteQueryController {
    private final VoteQueryService voteQueryService;

    @Autowired
    public VoteQueryController(VoteQueryService voteQueryService){
        this.voteQueryService = voteQueryService;
    }
    @GetMapping
    public List<VoteQueryListResponseDTO> findAll(){
        return voteQueryService.findAll();
    }
    @GetMapping("/{id}")
    public VoteQueryDetailResponseDTO findById(@PathVariable("id") int id){
        return voteQueryService.findById(id);
    }
    @PostMapping("/search")
    public List<VoteQueryListResponseDTO> search(@RequestBody VoteSearchDTO voteSearchDTO){
        return voteQueryService.search(voteSearchDTO);
    }

    @GetMapping("/member/{memberId}")
    public List<VoteQueryListResponseDTO> findByMemberId(@PathVariable("memberId") int memberId){
        return voteQueryService.findByMemberId(memberId);
    }

    @GetMapping("/challenger/{challengerId}")
    public List<VoteQueryListResponseDTO> findByChallengerId(@PathVariable("challengerId") int challengerId){
        return voteQueryService.findByChallengerId(challengerId);
    }
}

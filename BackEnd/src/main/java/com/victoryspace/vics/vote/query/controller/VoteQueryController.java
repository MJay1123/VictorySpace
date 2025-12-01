package com.victoryspace.vics.vote.query.controller;

import com.victoryspace.vics.vote.query.dto.VoteQueryDTO;
import com.victoryspace.vics.vote.query.dto.VoteSearchDTO;
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
    public List<VoteQueryDTO> findAll(){
        List<VoteQueryDTO> list = voteQueryService.findAll();
        return list;
    }
    @GetMapping("/{id}")
    public VoteQueryDTO findById(@PathVariable("id") int id){
        VoteQueryDTO voteQueryDTO = voteQueryService.findById(id);
        return voteQueryDTO;
    }
    @PostMapping("/search")
    public List<VoteQueryDTO> search(@RequestBody VoteSearchDTO voteSearchDTO){
        List<VoteQueryDTO> dtoList = voteQueryService.search(voteSearchDTO);
        return dtoList;
    }

    @GetMapping("/member/{memberId}")
    public List<VoteQueryDTO> findByMemberId(@PathVariable("memberId") int memberId){
        List<VoteQueryDTO> dtoList = voteQueryService.findByMemberId(memberId);
        return dtoList;
    }

    @GetMapping("/challenger/{challengerId}")
    public List<VoteQueryDTO> findByChallengerId(@PathVariable("challengerId") int challengerId){
        List<VoteQueryDTO> dtoList = voteQueryService.findByChallengerId(challengerId);
        return dtoList;
    }
}

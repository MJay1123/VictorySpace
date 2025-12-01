package com.victoryspace.vics.voter.query.controller;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.VoterSearchDTO;
import com.victoryspace.vics.voter.query.service.VoterQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voter")
public class VoterQueryController {
    private final VoterQueryService voterQueryService;

    @Autowired
    public VoterQueryController(VoterQueryService voterQueryService){
        this.voterQueryService = voterQueryService;
    }
    @GetMapping
    public List<VoterQueryDTO> findAll(){
        List<VoterQueryDTO> dtoList = voterQueryService.findAll();
        return dtoList;
    }
    @GetMapping("/{id}")
    public VoterQueryDTO findById(@PathVariable("id") int id){
        VoterQueryDTO voterQueryDTO = voterQueryService.findById(id);
        return voterQueryDTO;
    }

    @GetMapping("/vote/{voteId}")
    public List<VoterQueryDTO> findByVoteId(@PathVariable("voteId") int voteId){
        List<VoterQueryDTO> dtoList = voterQueryService.findByVoteId(voteId);
        return dtoList;
    }

    @GetMapping("/member/{memberId}")
    public List<VoterQueryDTO> findByMemberId(@PathVariable("memberId") int memberId){
        List<VoterQueryDTO> dtoList = voterQueryService.findByMemberId(memberId);
        return dtoList;
    }
}

package com.victoryspace.vics.voter.query.controller;

import com.victoryspace.vics.voter.query.dto.VoterQueryDTO;
import com.victoryspace.vics.voter.query.dto.VoterSearchDTO;
import com.victoryspace.vics.voter.query.dto.response.VoterListDTO;
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

    @GetMapping("/vote/{voteId}")
    public List<VoterListDTO> findByVoteId(@PathVariable("voteId") int voteId){
        return voterQueryService.findByVoteId(voteId);
    }
}

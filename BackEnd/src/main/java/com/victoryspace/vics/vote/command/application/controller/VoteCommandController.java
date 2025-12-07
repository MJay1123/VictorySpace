package com.victoryspace.vics.vote.command.application.controller;

import com.victoryspace.vics.vote.command.application.dto.VoteCommandDTO;
import com.victoryspace.vics.vote.command.application.service.VoteCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vote")
public class VoteCommandController {
    private final VoteCommandService voteCommandService;

    @Autowired
    public VoteCommandController(VoteCommandService voteCommandService) {
        this.voteCommandService = voteCommandService;
    }

    @PostMapping
    public VoteCommandDTO createVote(@RequestBody VoteCommandDTO voteCommandDTO) {
        VoteCommandDTO createdVoteDTO = voteCommandService.createVote(voteCommandDTO);
        return createdVoteDTO;
    }

    @PatchMapping("/{id}")
    public VoteCommandDTO updateVote(@PathVariable Integer id, @RequestBody VoteCommandDTO voteCommandDTO) {
        VoteCommandDTO updatedVoteDTO = voteCommandService.updateVote(id, voteCommandDTO);
        return updatedVoteDTO;
    }

    @PatchMapping("/challenge/{voteId}")
    public VoteCommandDTO challegeVote(@PathVariable Integer voteId, @RequestBody VoteCommandDTO voteCommandDTO) {
        VoteCommandDTO updatedVoteCommandDTO = voteCommandService.challengeVote(voteId, voteCommandDTO);
        return updatedVoteCommandDTO;
    }

    @DeleteMapping("/{id}")
    public VoteCommandDTO deleteVote(@PathVariable Integer id) {
        VoteCommandDTO deletedVoteDTO = voteCommandService.deleteVote(id);
        return deletedVoteDTO;
    }
}

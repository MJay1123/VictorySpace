package com.victoryspace.vics.vote.command.application.controller;

import com.victoryspace.vics.vote.command.application.dto.request.VoteChallengeRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.request.VoteCreateRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.request.VoteUpdateRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteChallengeResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteCreateResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteDeleteResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteUpdateResponseDTO;
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
    public VoteCreateResponseDTO createVote(@RequestBody VoteCreateRequestDTO requestDTO) {
        return voteCommandService.createVote(requestDTO);
    }

    @PatchMapping("/{id}")
    public VoteUpdateResponseDTO updateVote(@PathVariable Integer id, @RequestBody VoteUpdateRequestDTO requestDTO) {
        return voteCommandService.updateVote(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public VoteDeleteResponseDTO deleteVote(@PathVariable Integer id) {
        return voteCommandService.deleteVote(id);
    }

    @PatchMapping("/challenge/{voteId}")
    public VoteChallengeResponseDTO challengeVote(@PathVariable Integer voteId, @RequestBody VoteChallengeRequestDTO requestDTO) {
        return voteCommandService.challengeVote(voteId, requestDTO);
    }
}

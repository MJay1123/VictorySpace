package com.victoryspace.vics.voter.command.application.controller;

import com.victoryspace.vics.voter.command.application.dto.VoterCommandDTO;
import com.victoryspace.vics.voter.command.application.service.VoterCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voter")
public class VoterCommandController {
    private final VoterCommandService voterCommandService;

    @Autowired
    public VoterCommandController(VoterCommandService voterCommandService) {
        this.voterCommandService = voterCommandService;
    }

    @PostMapping
    public VoterCommandDTO createVoter(@RequestBody VoterCommandDTO voterCommandDTO) {
        VoterCommandDTO createdVoterDTO = voterCommandService.createVoter(voterCommandDTO);
        return createdVoterDTO;
    }

    @DeleteMapping("/{id}")
    public VoterCommandDTO deleteVoter(@PathVariable Integer id) {
        VoterCommandDTO deletedVoterDTO = voterCommandService.deleteVoter(id);
        return deletedVoterDTO;
    }
}

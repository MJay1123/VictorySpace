package com.victoryspace.vics.voter.command.application.controller;

import com.victoryspace.vics.voter.command.application.dto.request.VoterCreateRequestDTO;
import com.victoryspace.vics.voter.command.application.dto.response.VoterCreateResponseDTO;
import com.victoryspace.vics.voter.command.application.dto.response.VoterDeleteResponseDTO;
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
    public VoterCreateResponseDTO createVoter(@RequestBody VoterCreateRequestDTO requestDTO) {
        return voterCommandService.createVoter(requestDTO);
    }

    @DeleteMapping("/{id}")
    public VoterDeleteResponseDTO deleteVoter(@PathVariable Integer id) {
        return voterCommandService.deleteVoter(id);
    }
}

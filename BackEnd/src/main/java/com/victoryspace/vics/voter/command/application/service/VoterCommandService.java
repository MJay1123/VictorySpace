package com.victoryspace.vics.voter.command.application.service;

import com.victoryspace.vics.voter.command.application.dto.VoterCommandDTO;

public interface VoterCommandService {
    VoterCommandDTO createVoter(VoterCommandDTO voterCommandDTO);

    VoterCommandDTO deleteVoter(Integer id);
}

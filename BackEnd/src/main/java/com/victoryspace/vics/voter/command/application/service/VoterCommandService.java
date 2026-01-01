package com.victoryspace.vics.voter.command.application.service;

import com.victoryspace.vics.voter.command.application.dto.VoterCommandDTO;
import com.victoryspace.vics.voter.command.application.dto.request.VoterCreateRequestDTO;
import com.victoryspace.vics.voter.command.application.dto.response.VoterCreateResponseDTO;
import com.victoryspace.vics.voter.command.application.dto.response.VoterDeleteResponseDTO;

public interface VoterCommandService {
    VoterCreateResponseDTO createVoter(VoterCreateRequestDTO requestDTO);

    VoterDeleteResponseDTO deleteVoter(Integer id);
}

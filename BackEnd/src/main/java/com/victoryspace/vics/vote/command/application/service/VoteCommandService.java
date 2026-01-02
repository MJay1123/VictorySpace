package com.victoryspace.vics.vote.command.application.service;

import com.victoryspace.vics.vote.command.application.dto.request.VoteChallengeRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.request.VoteCreateRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.request.VoteUpdateRequestDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteChallengeResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteCreateResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteDeleteResponseDTO;
import com.victoryspace.vics.vote.command.application.dto.response.VoteUpdateResponseDTO;

public interface VoteCommandService {
    VoteCreateResponseDTO createVote(VoteCreateRequestDTO requestDTO);

    VoteUpdateResponseDTO updateVote(Integer id, VoteUpdateRequestDTO requestDTO);

    VoteDeleteResponseDTO deleteVote(Integer id);

    VoteChallengeResponseDTO challengeVote(Integer voteId, VoteChallengeRequestDTO requestDTO);
}

package com.victoryspace.vics.vote.command.application.service;

import com.victoryspace.vics.vote.command.application.dto.VoteCommandDTO;

public interface VoteCommandService {
    VoteCommandDTO createVote(VoteCommandDTO voteCommandDTO);

    VoteCommandDTO updateVote(Integer id, VoteCommandDTO voteCommandDTO);

    VoteCommandDTO deleteVote(Integer id);

    VoteCommandDTO challengeVote(Integer voteId, VoteCommandDTO voteCommandDTO);
}

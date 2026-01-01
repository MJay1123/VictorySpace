package com.victoryspace.vics.voter.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoterDeleteResponseDTO {
    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private String content;
}

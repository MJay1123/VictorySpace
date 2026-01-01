package com.victoryspace.vics.voter.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoterCreateResponseDTO {
    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private String content;
}

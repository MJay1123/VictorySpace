package com.victoryspace.vics.voter.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoterCreateRequestDTO {
    private Integer voteId;
    private Integer memberId;
    private String content;
}

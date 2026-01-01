package com.victoryspace.vics.vote.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteChallengeRequestDTO {
    private Integer challengerId;
    private String challengerContent;
}

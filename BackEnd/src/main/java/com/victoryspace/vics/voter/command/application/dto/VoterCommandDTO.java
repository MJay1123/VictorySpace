package com.victoryspace.vics.voter.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoterCommandDTO {
    private Integer id;
    private Integer voteId;
    private Integer memberId;
    private String content;
}

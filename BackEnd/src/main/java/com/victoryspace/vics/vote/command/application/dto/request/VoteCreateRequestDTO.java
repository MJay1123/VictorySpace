package com.victoryspace.vics.vote.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteCreateRequestDTO {
    private String title;
    private Integer categoryId;
    private Integer memberId;
    private String content;
    private String duration;
}

package com.victoryspace.vics.vote.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteUpdateRequestDTO {

    private String title;
    private Integer categoryId;
    private String content;
    private String duration;
}

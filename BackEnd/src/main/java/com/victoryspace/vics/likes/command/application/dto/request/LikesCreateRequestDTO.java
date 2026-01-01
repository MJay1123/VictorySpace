package com.victoryspace.vics.likes.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesCreateRequestDTO {
    private Integer voteId;
    private Integer memberId;
}

package com.victoryspace.vics.vote.query.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteQueryCountDTO {
    private Integer homeCount;
    private Integer awayCount;
    private Integer neutralCount;
}

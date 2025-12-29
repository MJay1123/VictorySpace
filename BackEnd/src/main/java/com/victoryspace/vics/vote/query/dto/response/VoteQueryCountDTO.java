package com.victoryspace.vics.vote.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteQueryCountDTO {
    private Integer homeCount;
    private Integer awayCount;
    private Integer neutralCount;
}

package com.victoryspace.vics.voter.query.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoterListDTO {
    private Integer id;
    private String memberNickname;
    private String content;
}

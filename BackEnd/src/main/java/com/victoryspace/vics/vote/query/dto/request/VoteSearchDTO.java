package com.victoryspace.vics.vote.query.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteSearchDTO {
    private String title;
    private Integer categoryId;
    private String nickname;
    private String content;
}

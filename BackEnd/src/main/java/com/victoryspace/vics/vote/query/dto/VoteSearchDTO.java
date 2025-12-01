package com.victoryspace.vics.vote.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteSearchDTO {
    private String title;
    private Integer categoryId;
    private String nickname;
    private String content;
}

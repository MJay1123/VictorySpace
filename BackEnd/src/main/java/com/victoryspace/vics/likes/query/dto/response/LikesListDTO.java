package com.victoryspace.vics.likes.query.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LikesListDTO {
    private Integer id;
    private Integer memberId;
    private String memberNickname;
}

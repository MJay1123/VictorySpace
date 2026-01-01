package com.victoryspace.vics.vote.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteCreateResponseDTO {

    private Integer id;
    private String title;
    private Integer categoryId;
    private Integer memberId;
    private String content;
    private String duration;
    private LocalDateTime createdAt;
}

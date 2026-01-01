package com.victoryspace.vics.vote.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteDeleteResponseDTO {
    private Integer id;
    private LocalDateTime deletedAt;
}

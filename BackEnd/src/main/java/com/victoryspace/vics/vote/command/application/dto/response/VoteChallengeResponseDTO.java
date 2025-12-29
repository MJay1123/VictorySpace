package com.victoryspace.vics.vote.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class VoteChallengeResponseDTO {
    private Integer id;
    private Integer challengerId;
    private String challengerContent;
    private LocalDateTime updatedAt;
    private LocalDateTime endedAt;
}

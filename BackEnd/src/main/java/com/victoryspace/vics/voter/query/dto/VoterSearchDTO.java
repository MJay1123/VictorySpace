package com.victoryspace.vics.voter.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoterSearchDTO {
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

package com.victoryspace.vics.vote.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteQueryDTO {
    private Integer id;
    private String title;
    private Integer memberId;
    private String content;
    private Integer challengerId;
    private String challengerContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private String duration;
    private LocalDateTime endedAt;
}

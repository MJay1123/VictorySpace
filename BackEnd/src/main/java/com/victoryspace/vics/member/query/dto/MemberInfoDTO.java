package com.victoryspace.vics.member.query.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class MemberInfoDTO {
    private String name;
    private String gender;
    private LocalDate birthday;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String gradeName;
    private String gradeSymbol;
    private String profile;
    private Integer point;
    private String role;
}

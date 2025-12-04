package com.victoryspace.vics.member.command.application.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCommandDTO {
    private Integer id;
    private String name;
    private String gender;
    private LocalDate birthday;
    private String nickname;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Integer gradeId;
    private String profile;
    private Integer point;
    private String role;
}

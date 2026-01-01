package com.victoryspace.vics.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class RegisterResponseDTO {
    private String name;
    private String gender;
    private LocalDate birthday;
    private String nickname;
    private String email;
    private LocalDateTime createdAt;
}

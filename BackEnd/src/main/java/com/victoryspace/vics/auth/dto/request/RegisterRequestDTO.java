package com.victoryspace.vics.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class RegisterRequestDTO {
    private String name;
    private String gender;
    private LocalDate birthday;
    private String nickname;
    private String password;
    private String email;
}

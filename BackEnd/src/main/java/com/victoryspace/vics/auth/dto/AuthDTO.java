package com.victoryspace.vics.auth.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuthDTO {
    private String name;
    private String gender;
    private LocalDate birthday;
    private String nickname;
    private String password;
    private String email;
}

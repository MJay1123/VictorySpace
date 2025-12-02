package com.victoryspace.vics.auth.controller;

import com.victoryspace.vics.auth.dto.AuthDTO;
import com.victoryspace.vics.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public String register(@RequestBody AuthDTO authDTO) {
        String result = authService.register(authDTO);
        if(result.equals("emailExists")){
            return "Email is already exists";
        } else if(result.equals("nicknameExists")){
            return "Nickname is already exists";
        } else {
            return "Join is finished! Your nickname is " + result;
        }
    }
}

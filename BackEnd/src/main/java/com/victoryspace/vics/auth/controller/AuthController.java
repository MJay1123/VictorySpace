package com.victoryspace.vics.auth.controller;

import com.victoryspace.vics.auth.dto.AuthDTO;
import com.victoryspace.vics.auth.dto.request.RegisterRequestDTO;
import com.victoryspace.vics.auth.dto.response.RegisterResponseDTO;
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
    public RegisterResponseDTO register(@RequestBody RegisterRequestDTO requestDTO) {
        return authService.register(requestDTO);
    }
}

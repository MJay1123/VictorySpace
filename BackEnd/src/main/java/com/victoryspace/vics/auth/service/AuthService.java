package com.victoryspace.vics.auth.service;

import com.victoryspace.vics.auth.dto.AuthDTO;
import com.victoryspace.vics.auth.dto.request.RegisterRequestDTO;
import com.victoryspace.vics.auth.dto.response.RegisterResponseDTO;
import com.victoryspace.vics.auth.exception.AuthException;
import com.victoryspace.vics.common.error.ErrorCode;
import com.victoryspace.vics.member.command.application.mapper.MemberCommandMapper;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import com.victoryspace.vics.member.command.domain.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberCommandMapper memberCommandMapper;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.memberCommandMapper = new MemberCommandMapper();
    }

    public RegisterResponseDTO register(RegisterRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        if(memberRepository.existsByEmail(email)){
            throw new AuthException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        String nickname =  requestDTO.getNickname();
        if(memberRepository.existsByNickname(nickname)){
            throw new AuthException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }
        String name = requestDTO.getName();
        String gender = requestDTO.getGender();
        LocalDate birthday = requestDTO.getBirthday();
        String password = requestDTO.getPassword();
        MemberEntity memberEntity = MemberEntity.create(
                name,
                gender,
                birthday,
                nickname,
                bCryptPasswordEncoder.encode(password),
                email
        );
        memberRepository.save(memberEntity);
        return memberCommandMapper.toRegisterResponseDTO(memberEntity);
    }
}

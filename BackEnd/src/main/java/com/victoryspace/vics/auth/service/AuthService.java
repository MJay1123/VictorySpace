package com.victoryspace.vics.auth.service;

import com.victoryspace.vics.auth.dto.AuthDTO;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import com.victoryspace.vics.member.command.domain.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String register(AuthDTO authDTO) {
        String email = authDTO.getEmail();
        boolean emailExists = memberRepository.existsByEmail(email);
        if(emailExists){
            return "emailExists";
        }
        String nickname =  authDTO.getNickname();
        boolean nicknameExists = memberRepository.existsByNickname(nickname);
        if(nicknameExists){
            return "nicknameExists";
        }
        String name = authDTO.getName();
        String gender = authDTO.getGender();
        LocalDate birthday = authDTO.getBirthday();
        String password = authDTO.getPassword();
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName(name);
        memberEntity.setGender(gender);
        memberEntity.setBirthday(birthday);
        memberEntity.setNickname(nickname);
        memberEntity.setEmail(email);
        memberEntity.setPassword(bCryptPasswordEncoder.encode(password));
        memberEntity.setCreatedAt(LocalDateTime.now());
        memberEntity.setGradeId(1);
        memberEntity.setPoint(1000);
        MemberEntity createdEntity = memberRepository.save(memberEntity);
        return createdEntity.getNickname();
    }
}

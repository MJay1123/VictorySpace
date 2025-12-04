package com.victoryspace.vics.auth.service;

import com.victoryspace.vics.auth.dto.CustomUserDetails;
import com.victoryspace.vics.member.command.domain.aggregate.MemberEntity;
import com.victoryspace.vics.member.command.domain.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        if(memberEntity == null){
            throw new UsernameNotFoundException("존재하지 않는 이메일입니다." + email);
        }
        return new CustomUserDetails(memberEntity);
    }
}

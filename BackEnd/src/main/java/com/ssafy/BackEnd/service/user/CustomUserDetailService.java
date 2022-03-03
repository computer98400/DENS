package com.ssafy.BackEnd.service.user;

import com.ssafy.BackEnd.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails user = (UserDetails)userRepository.findByEmail(email);
        if(user == null) {
            System.out.println("no user");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        return user;
    }
}
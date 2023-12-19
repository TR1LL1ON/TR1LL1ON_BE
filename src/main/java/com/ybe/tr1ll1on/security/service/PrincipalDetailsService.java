package com.ybe.tr1ll1on.security.service;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import com.ybe.tr1ll1on.security.exception.SecurityException;
import com.ybe.tr1ll1on.security.model.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new SecurityException(SecurityExceptionCode.USER_NOT_FOUND));

        return createUserDetails(user);
    }

    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SecurityException(SecurityExceptionCode.USER_NOT_FOUND));

        return createUserDetails(user);
    }

    public UserDetails createUserDetails(User user) {

        return PrincipalDetails.builder()
                .username(String.valueOf(user.getId()))
                .password(user.getPassword())
                .authority(user.getAuthority().toString())
                .build();
    }
}
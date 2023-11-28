package com.ybe.tr1ll1on.security.service;

import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import com.ybe.tr1ll1on.security.exception.UserNotFoundException;
import com.ybe.tr1ll1on.security.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isPresent()) {
            return UserPrincipal.create(byEmail.get());
        } else {
            throw new UserNotFoundException(SecurityExceptionCode.USER_NOT_FOUND);
        }
    }

    public UserDetails loadUserById(String userIdString) {
        Long userId = Long.parseLong(userIdString);
        Optional<User> byId = userRepository.findById(userId);
        if (byId.isPresent()) {
            return UserPrincipal.create(byId.get());
        } else {
            throw new UserNotFoundException(SecurityExceptionCode.USER_NOT_FOUND);
        }
    }
}

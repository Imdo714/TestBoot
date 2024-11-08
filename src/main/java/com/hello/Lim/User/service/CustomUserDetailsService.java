package com.hello.Lim.User.service;

import com.hello.Lim.User.dto.CustomUserDetails;
import com.hello.Lim.User.entity.UserEntity;
import com.hello.Lim.User.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);

        log.info("username = {}", username);
        log.info("userData = {}", userData);

        if (userData == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new CustomUserDetails(userData);
    }
}

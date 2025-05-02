package com.yarzar.booking_system.core_module.security;

import com.yarzar.booking_system.core_module.entity.User;
import com.yarzar.booking_system.core_module.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailService.class);

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("loadUserByUsername() username: {}", username);

        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s is not found.", username)));

        UUID region = null;
        String phoneNumber = null;
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");


        return CustomUserDetail.create(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getVerified(),
                authorities
        );
    }
}

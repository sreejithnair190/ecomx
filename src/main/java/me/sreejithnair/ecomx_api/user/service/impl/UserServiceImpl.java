package me.sreejithnair.ecomx_api.user.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.user.entity.User;
import me.sreejithnair.ecomx_api.user.repository.UserRepository;
import me.sreejithnair.ecomx_api.user.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmailWithRoles(username)
                .orElseThrow(() -> new BadCredentialsException("User with email "+username+" not found!"));
    }

    @Override
    public User getUserByUserId(Long userId) {
        return userRepository
                .findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id "+userId+" not found!"));
    }
}

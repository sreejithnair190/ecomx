package me.sreejithnair.ecomx_api.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.auth.dto.AuthTokenDto;
import me.sreejithnair.ecomx_api.auth.dto.SignInDto;
import me.sreejithnair.ecomx_api.auth.dto.SignUpDto;
import me.sreejithnair.ecomx_api.auth.service.AuthService;
import me.sreejithnair.ecomx_api.auth.service.JwtService;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.user.entity.Role;
import me.sreejithnair.ecomx_api.user.entity.User;
import me.sreejithnair.ecomx_api.user.enums.UserRoles;
import me.sreejithnair.ecomx_api.user.repository.RoleRepository;
import me.sreejithnair.ecomx_api.user.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthTokenDto signUp(SignUpDto signUpDto) {
        Optional<User> existingUser = userRepository.findByEmail(signUpDto.getEmail());

        if (existingUser.isPresent()) {
            throw new BadCredentialsException("User with email already exists "+ signUpDto.getEmail());
        }

        User userToCreate = modelMapper.map(signUpDto, User.class);
        userToCreate.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        String customer = String.valueOf(UserRoles.CUSTOMER);
        Role customerRole = roleRepository.findByName(customer)
                .orElseThrow(() -> new ResourceNotFoundException("Role "+customer+" not found"));
        userToCreate.getRoles().add(customerRole);

        User savedUser = userRepository.save(userToCreate);

        return generateAuthTokens(savedUser);
    }

    @Override
    public AuthTokenDto signIn(SignInDto signInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        return generateAuthTokens(user);
    }

    @Override
    public String forgotPassword(String email) {
        return "";
    }

    @Override
    public AuthTokenDto refreshTokens(String refreshToken) {
        return null;
    }

    @Override
    public AuthTokenDto verifyEmail(String token) {
        return null;
    }

    private AuthTokenDto generateAuthTokens(User user) {
        String accessToken = jwtService.generateToken(user, 10);
        String refreshToken = jwtService.generateToken(user, 1440);

        return AuthTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

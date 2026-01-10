package me.sreejithnair.ecomx_api.auth.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.auth.dto.AuthTokenDto;
import me.sreejithnair.ecomx_api.auth.dto.SignInDto;
import me.sreejithnair.ecomx_api.auth.dto.SignUpDto;
import me.sreejithnair.ecomx_api.auth.service.AuthService;
import me.sreejithnair.ecomx_api.auth.service.JwtService;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.event.model.UserCreatedEvent;
import me.sreejithnair.ecomx_api.event.publisher.UserEventPublisher;
import me.sreejithnair.ecomx_api.user.core.entity.User;
import me.sreejithnair.ecomx_api.user.core.repository.UserRepository;
import me.sreejithnair.ecomx_api.user.core.service.UserService;
import me.sreejithnair.ecomx_api.user.role.entity.Role;
import me.sreejithnair.ecomx_api.user.role.enums.UserRoles;
import me.sreejithnair.ecomx_api.user.role.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserEventPublisher userEventPublisher;
    private final UserService userService;

    @Value("${jwt.access-token-expiry}")
    private int accessTokenExpiry;

    @Value("${jwt.refresh-token-expiry}")
    private int refreshTokenExpiry;

    @Override
    @Transactional
    public AuthTokenDto signUp(SignUpDto signUpDto) {
        Optional<User> existingUser = userRepository.findByEmailAndDeletedAtIsNull(signUpDto.getEmail());

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

        // Publish user created event
        UserCreatedEvent event = UserCreatedEvent.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .createdAt(Instant.now())
                .build();
        userEventPublisher.publishUserRegistered(event);

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
       Long userId = jwtService.getUserIdFromToken(refreshToken);
       User user = userService.getUserByUserId(userId);
       return generateAuthTokens(user);
    }

    @Override
    public AuthTokenDto verifyEmail(String token) {
        return null;
    }

    private AuthTokenDto generateAuthTokens(User user) {
        String accessToken = jwtService.generateToken(user, accessTokenExpiry);
        String refreshToken = jwtService.generateToken(user, refreshTokenExpiry);

        return AuthTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}

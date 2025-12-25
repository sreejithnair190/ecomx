package me.sreejithnair.ecomx_api.auth.service;

import me.sreejithnair.ecomx_api.auth.dto.AuthTokenDto;
import me.sreejithnair.ecomx_api.auth.dto.SignInDto;
import me.sreejithnair.ecomx_api.auth.dto.SignUpDto;

public interface AuthService {

    AuthTokenDto signUp(SignUpDto signUpDto);

    AuthTokenDto signIn(SignInDto signInDto);

    String forgotPassword(String email);

    AuthTokenDto refreshTokens(String refreshToken);

    AuthTokenDto verifyEmail(String token);
}

package me.sreejithnair.ecomx_api.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import me.sreejithnair.ecomx_api.auth.dto.AuthTokenDto;
import me.sreejithnair.ecomx_api.auth.dto.SignInDto;
import me.sreejithnair.ecomx_api.auth.dto.SignUpDto;
import me.sreejithnair.ecomx_api.auth.service.AuthService;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;
import static me.sreejithnair.ecomx_api.common.constant.AppConstant.REFRESH_TOKEN;

@RestController
@RequestMapping(API_VERSION_V1 + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<AuthTokenDto>> signUp(
            @Valid @RequestBody SignUpDto signUpDto,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        AuthTokenDto authTokenDto = authService.signUp(signUpDto);

        Cookie cookie = new Cookie(REFRESH_TOKEN, authTokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return ApiResponse.created(authTokenDto, "Signed Up Successfully!");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<AuthTokenDto>> signIn(
            @Valid @RequestBody SignInDto signInDto,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        AuthTokenDto authTokenDto = authService.signIn(signInDto);

        Cookie cookie = new Cookie(REFRESH_TOKEN, authTokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return ApiResponse.ok(authTokenDto, "Signed In Successfully!");
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(String email) {
        String message = authService.forgotPassword(email);
        return null;
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthTokenDto>> refreshTokens(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        String refreshToken = Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> REFRESH_TOKEN.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the cookie"));

        AuthTokenDto authTokenDto = authService.refreshTokens(refreshToken);

        Cookie cookie = new Cookie(REFRESH_TOKEN, authTokenDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);

        return ApiResponse.ok(authTokenDto, "Token Refreshed Successfully!");
    }

    @PatchMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(String token) {
        AuthTokenDto authTokenDto = authService.verifyEmail(token);
        return null;
    }
}

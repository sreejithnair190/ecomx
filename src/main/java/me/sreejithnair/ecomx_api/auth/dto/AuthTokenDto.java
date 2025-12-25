package me.sreejithnair.ecomx_api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenDto {
    private String accessToken;
    private String refreshToken;
}

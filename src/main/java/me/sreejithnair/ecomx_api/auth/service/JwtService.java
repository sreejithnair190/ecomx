package me.sreejithnair.ecomx_api.auth.service;

import me.sreejithnair.ecomx_api.user.entity.User;

public interface JwtService {
    String generateToken(User user, long expiryMinutes);
    Long getUserIdFromToken(String token);
}

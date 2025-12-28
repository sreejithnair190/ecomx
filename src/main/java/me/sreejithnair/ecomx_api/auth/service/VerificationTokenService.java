package me.sreejithnair.ecomx_api.auth.service;

public interface VerificationTokenService {
    String generateVerificationToken(Long userId, String email);
    String buildVerificationLink(String token);
    boolean verifyToken(String token);
    Long getUserIdFromToken(String token);
}

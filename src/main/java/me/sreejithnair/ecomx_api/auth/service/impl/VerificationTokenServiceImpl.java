package me.sreejithnair.ecomx_api.auth.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.auth.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Value("${app.verification.secret}")
    private String secretKey;

    @Value("${app.verification.expiration-hours}")
    private int expirationHours;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public String generateVerificationToken(Long userId, String email) {
        long expiryTime = Instant.now().plusSeconds(expirationHours * 3600L).toEpochMilli();
        String data = userId + ":" + email + ":" + expiryTime;
        String signature = sign(data);
        return Base64.getUrlEncoder().encodeToString((data + ":" + signature).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String buildVerificationLink(String token) {
        return baseUrl + "/api/auth/verify-email?token=" + token;
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            if (parts.length != 4) return false;

            String data = parts[0] + ":" + parts[1] + ":" + parts[2];
            String signature = parts[3];
            long expiryTime = Long.parseLong(parts[2]);

            return sign(data).equals(signature) && Instant.now().toEpochMilli() < expiryTime;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserIdFromToken(String token) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            return Long.parseLong(parts[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private String sign(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign token", e);
        }
    }
}

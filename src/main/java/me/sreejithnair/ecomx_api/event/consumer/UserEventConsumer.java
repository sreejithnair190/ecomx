package me.sreejithnair.ecomx_api.event.consumer;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sreejithnair.ecomx_api.auth.service.VerificationTokenService;
import me.sreejithnair.ecomx_api.config.RabbitMQConfig;
import me.sreejithnair.ecomx_api.event.model.UserCreatedEvent;
import me.sreejithnair.ecomx_api.notification.service.EmailService;
import me.sreejithnair.ecomx_api.user.entity.User;
import me.sreejithnair.ecomx_api.user.enums.UserStatus;
import me.sreejithnair.ecomx_api.user.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventConsumer {

    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final UserRepository userRepository;

    @Value("${app.verification.expiration-hours:24}")
    private int expirationHours;

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTERED_QUEUE)
    public void handleUserRegistered(UserCreatedEvent event) {
        log.info("User Created with email: {}", event.getEmail());

        sendWelcomeEmail(event);
        sendAdminNotification(event);
    }

    private void sendWelcomeEmail(UserCreatedEvent event) {
        try {
            String token = verificationTokenService.generateVerificationToken(event.getUserId(), event.getEmail());
            String verificationLink = verificationTokenService.buildVerificationLink(token);
            String userName = event.getFirstName() + " " + event.getLastName();

            Map<String, Object> variables = Map.of(
                    "userName", userName,
                    "verificationLink", verificationLink,
                    "expirationHours", expirationHours
            );

            emailService.sendHtmlEmail(event.getEmail(), "Welcome to Ecomx!", "welcome-user", variables);
            log.info("Welcome email sent to: {}", event.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send welcome email to {}: {}", event.getEmail(), e.getMessage());
        }
    }

    private void sendAdminNotification(UserCreatedEvent event) {
        String userName = event.getFirstName() + " " + event.getLastName();
        String registeredAt = event.getCreatedAt()
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Map<String, Object> variables = Map.of(
                "userName", userName,
                "userEmail", event.getEmail(),
                "userId", event.getUserId(),
                "registeredAt", registeredAt
        );

        List<User> admins = userRepository.findActiveUsersByRoles(
                List.of("SUPER_ADMIN", "ADMIN"),
                UserStatus.ACTIVE
        );

        for (User admin : admins) {
            try {
                emailService.sendHtmlEmail(admin.getEmail(), "New User Registered - " + event.getEmail(), "admin/admin-new-user", variables);
                log.info("Admin notification sent to: {}", admin.getEmail());
            } catch (MessagingException e) {
                log.error("Failed to send admin notification to {}: {}", admin.getEmail(), e.getMessage());
            }
        }
    }
}

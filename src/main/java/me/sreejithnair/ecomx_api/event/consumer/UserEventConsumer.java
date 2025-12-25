package me.sreejithnair.ecomx_api.event.consumer;

import lombok.extern.slf4j.Slf4j;
import me.sreejithnair.ecomx_api.config.RabbitMQConfig;
import me.sreejithnair.ecomx_api.event.model.UserCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventConsumer {

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTERED_QUEUE)
    public void handleUserRegistered(UserCreatedEvent event) {
        log.info("User Created with email: {}", event.getEmail());
        log.info("Send Welcome and email verification link to user: {}", event.getEmail());
        log.info("Send Email To Admin: New user registered - {}", event.getEmail());
    }
}

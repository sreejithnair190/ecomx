package me.sreejithnair.ecomx_api.event.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sreejithnair.ecomx_api.config.RabbitMQConfig;
import me.sreejithnair.ecomx_api.event.model.UserCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishUserRegistered(UserCreatedEvent event) {
        log.info("Publishing UserRegistered event for email: {}", event.getEmail());
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTERED_QUEUE, event);
    }
}

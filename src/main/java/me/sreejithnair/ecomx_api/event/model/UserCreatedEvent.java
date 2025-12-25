package me.sreejithnair.ecomx_api.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent implements Serializable {
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Instant createdAt;
}

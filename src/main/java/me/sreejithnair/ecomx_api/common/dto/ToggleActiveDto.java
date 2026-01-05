package me.sreejithnair.ecomx_api.common.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToggleActiveDto {
    @NotNull(message = "isActive field is required")
    private Boolean isActive;
}

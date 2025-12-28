package me.sreejithnair.ecomx_api.user.address.dto;

import lombok.Data;
import me.sreejithnair.ecomx_api.user.address.enums.AddressLabel;
import me.sreejithnair.ecomx_api.user.address.enums.AddressType;

import java.time.LocalDateTime;

@Data
public class AddressResponse {

    private Long id;
    private AddressType addressType;
    private AddressLabel addressLabel;
    private String addressName;
    private String fullName;
    private String phone;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

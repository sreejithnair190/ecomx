package me.sreejithnair.ecomx_api.user.address.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import me.sreejithnair.ecomx_api.user.address.enums.AddressLabel;
import me.sreejithnair.ecomx_api.user.address.enums.AddressType;

@Data
public class AddressRequest {

    private AddressType addressType = AddressType.BOTH;

    private AddressLabel addressLabel = AddressLabel.HOME;

    @Size(max = 100)
    private String addressName;

    @Size(max = 200)
    private String fullName;

    @Size(max = 20)
    private String phone;

    @NotBlank
    @Size(max = 255)
    private String addressLine1;

    @Size(max = 255)
    private String addressLine2;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    private String state;

    @NotBlank
    @Size(max = 20)
    private String postalCode;

    @NotBlank
    @Size(max = 100)
    private String country;

    private Boolean isDefault = false;
}

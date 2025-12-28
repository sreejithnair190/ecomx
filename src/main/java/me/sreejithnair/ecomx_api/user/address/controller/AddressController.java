package me.sreejithnair.ecomx_api.user.address.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.dto.ApiResponse;
import me.sreejithnair.ecomx_api.user.address.dto.AddressRequest;
import me.sreejithnair.ecomx_api.user.address.dto.AddressResponse;
import me.sreejithnair.ecomx_api.user.address.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@RestController
@RequestMapping(API_VERSION_V1 + "/users/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses(@PathVariable Long userId) {
        List<AddressResponse> addresses = addressService.getAddressesByUserId(userId);
        return ApiResponse.ok(addresses);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        AddressResponse address = addressService.getAddressById(userId, addressId);
        return ApiResponse.ok(address);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @PathVariable Long userId,
            @Valid @RequestBody AddressRequest request
    ) {
        AddressResponse address = addressService.createAddress(userId, request);
        return ApiResponse.created(address, "Address created successfully!");
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request
    ) {
        AddressResponse address = addressService.updateAddress(userId, addressId, request);
        return ApiResponse.ok(address, "Address updated successfully!");
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(userId, addressId);
        return ApiResponse.noContent();
    }

    @PatchMapping("/{addressId}/default")
    public ResponseEntity<ApiResponse<AddressResponse>> setDefaultAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        AddressResponse address = addressService.setDefaultAddress(userId, addressId);
        return ApiResponse.ok(address, "Default address updated!");
    }
}

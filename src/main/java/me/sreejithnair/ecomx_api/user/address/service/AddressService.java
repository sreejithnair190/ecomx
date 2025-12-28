package me.sreejithnair.ecomx_api.user.address.service;

import me.sreejithnair.ecomx_api.user.address.dto.AddressRequest;
import me.sreejithnair.ecomx_api.user.address.dto.AddressResponse;

import java.util.List;

public interface AddressService {

    List<AddressResponse> getAddressesByUserId(Long userId);

    AddressResponse getAddressById(Long userId, Long addressId);

    AddressResponse createAddress(Long userId, AddressRequest request);

    AddressResponse updateAddress(Long userId, Long addressId, AddressRequest request);

    void deleteAddress(Long userId, Long addressId);

    AddressResponse setDefaultAddress(Long userId, Long addressId);
}

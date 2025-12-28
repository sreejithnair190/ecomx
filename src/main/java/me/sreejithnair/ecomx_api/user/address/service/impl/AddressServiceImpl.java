package me.sreejithnair.ecomx_api.user.address.service.impl;

import lombok.RequiredArgsConstructor;
import me.sreejithnair.ecomx_api.common.exception.ResourceNotFoundException;
import me.sreejithnair.ecomx_api.user.address.dto.AddressRequest;
import me.sreejithnair.ecomx_api.user.address.dto.AddressResponse;
import me.sreejithnair.ecomx_api.user.address.entity.Address;
import me.sreejithnair.ecomx_api.user.address.repository.AddressRepository;
import me.sreejithnair.ecomx_api.user.address.service.AddressService;
import me.sreejithnair.ecomx_api.user.core.entity.User;
import me.sreejithnair.ecomx_api.user.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Override
    public List<AddressResponse> getAddressesByUserId(Long userId) {
        return addressRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public AddressResponse getAddressById(Long userId, Long addressId) {
        Address address = findAddressByIdAndUserId(addressId, userId);
        return toResponse(address);
    }

    @Override
    @Transactional
    public AddressResponse createAddress(Long userId, AddressRequest request) {
        User user = userService.getUserByUserId(userId);

        Address address = modelMapper.map(request, Address.class);
        address.setUser(user);

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefaultAddress(userId);
        }

        Address saved = addressRepository.save(address);
        return toResponse(saved);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long userId, Long addressId, AddressRequest request) {
        Address address = findAddressByIdAndUserId(addressId, userId);

        modelMapper.map(request, address);

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            clearDefaultAddress(userId);
            address.setIsDefault(true);
        }

        Address updated = addressRepository.save(address);
        return toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {
        Address address = findAddressByIdAndUserId(addressId, userId);
        addressRepository.delete(address);
    }

    @Override
    @Transactional
    public AddressResponse setDefaultAddress(Long userId, Long addressId) {
        Address address = findAddressByIdAndUserId(addressId, userId);

        clearDefaultAddress(userId);
        address.setIsDefault(true);

        Address updated = addressRepository.save(address);
        return toResponse(updated);
    }

    private Address findAddressByIdAndUserId(Long addressId, Long userId) {
        return addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + addressId + " not found!"));
    }

    private void clearDefaultAddress(Long userId) {
        addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .ifPresent(addr -> {
                    addr.setIsDefault(false);
                    addressRepository.save(addr);
                });
    }

    private AddressResponse toResponse(Address address) {
        return modelMapper.map(address, AddressResponse.class);
    }
}

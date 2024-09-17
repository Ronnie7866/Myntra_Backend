package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    static Address getAddress(Address address, Optional<Address> userAddressOptional) {
        Address existingAddress = userAddressOptional.get();

        // Update the address details
        existingAddress.setCity(address.getCity());
        existingAddress.setAddressType(address.getAddressType());
        existingAddress.setPhoneType(address.getPhoneType());
        existingAddress.setStreetAddress(address.getStreetAddress());
        existingAddress.setUser(address.getUser());
        existingAddress.setPinCode(address.getPinCode());
        return existingAddress;
    }

    Address save(Long userId, Address address);

    List<Address> getAll();

    Address updateAddressByUserId(Address address, Long userId);

    Address getAddressByUserId(Long id);

    String deleteAddressById(Long id);
}

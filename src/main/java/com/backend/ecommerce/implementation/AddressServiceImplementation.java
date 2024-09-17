package com.backend.ecommerce.implementation;

import com.backend.ecommerce.mapper.UserMapper;
import com.backend.ecommerce.entity.Address;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.exception.ResourceNotFoundException;
import com.backend.ecommerce.repository.AddressRepository;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.service.AddressService;
import com.backend.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressServiceImplementation implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final UserMapper customModelMapper;
    private final UserRepository userRepository;

    @Override
    public Address save(Long userId, Address address) {
        User user = userRepository.findById(userId).get();
        user.getAddressList().add(address);
        address.setUser(user);
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address updateAddressByUserId(Address address, Long userId) {
        // Fetch the user by ID
        User user = customModelMapper.reverse(userService.getUserById(userId));

        // Check if the user exists
        if (user != null) {
            // Fetch the address associated with the user
            Optional<Address> userAddressOptional = addressRepository.findByUserId(userId);

            if (userAddressOptional.isPresent()) {
                Address existingAddress = AddressService.getAddress(address, userAddressOptional);

                // Save the updated address
                return addressRepository.save(existingAddress);
            } else {
                // If the user does not have an address yet, create a new one
                address.setUser(user);
                return addressRepository.save(address);
            }
        } else {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }
    }

    @Override
    public Address getAddressByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
        return addressRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
    }

    @Override
    public String deleteAddressById(Long id) {
        addressRepository.deleteById(id);
        return "Adress deleted";
    }
}

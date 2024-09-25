package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.Address;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.records.AddressDTO;
import com.backend.ecommerce.records.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class UserMapper implements EntityDTOMapper<User, UserDTO> {

    private final AddressMapper addressMapper;

    @Autowired
    public UserMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public UserDTO apply(User user) {

        List<AddressDTO> addressDTOS = user.getAddressList().stream().map(addressMapper::apply).toList();

        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getDefaultPhoneNumber(), addressDTOS);

    }

//    public User reverse(UserDTO userDTO) {
//
//        List<Address> addressList = userDTO.addressList().stream().map(addressMapper::reverse).toList();  // this toList() method create immutable list which we can edit or modify
//
//        return new User(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.defaultPhoneNumber(), addressList);
//    }

//    public User reverse(UserDTO userDTO) {
//        List<Address> addressList = new ArrayList<>(userDTO.addressList().stream().map(addressMapper::reverse).toList());
//        return new User(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.defaultPhoneNumber(), addressList);
//    }

    public User reverse(UserDTO userDTO) {
        List<Address> addressList = new ArrayList<>(userDTO.addressList().stream().map(addressMapper::reverse).toList());
        User user = new User(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.defaultPhoneNumber(), addressList);
        // Do not set createdat here
        return user;
    }
}

package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.records.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements EntityDTOMapper<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole(), user.getDefaultPhoneNumber(), user.getAddressList());

    }

    public User reverse(UserDTO userDTO) {
        return new User(userDTO.firstName(), userDTO.lastName(), userDTO.email(), userDTO.defaultPhoneNumber(), userDTO.addressList());
    }
}

package com.backend.ecommerce.service;

import com.backend.ecommerce.records.UserDTO;

import java.util.List;

public interface UserService {
//    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long userId, UserDTO userDTO);

    void deleteUser(Long id);

    UserDTO getUserByEmail(String email);
}


package com.backend.ecommerce.implementation;

import com.backend.ecommerce.authentication.AuthenticationResponse;
import com.backend.ecommerce.enums.Role;
import com.backend.ecommerce.mapper.UserMapper;
import com.backend.ecommerce.dto.EmailDetails;
import com.backend.ecommerce.records.UserDTO;
import com.backend.ecommerce.exception.DuplicateEntryException;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.exception.UserNotFoundException;
import com.backend.ecommerce.repository.EmailService;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.security.JWTService;
import com.backend.ecommerce.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final UserMapper customModelMapper;
    private final EmailService emailService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, UserMapper customModelMapper, EmailService emailService, JWTService jwtService, @Qualifier("authenticationManager") AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.customModelMapper = customModelMapper;
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

//    public AuthenticationResponse createUser (RegisterRequest request) {
//        var user = User.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(Role.ADMIN)
//                .build();
//        User savedUser = userRepository.save(user);
//        UserDTO userDTO = customModelMapper.apply(savedUser);
//        return new AuthenticationResponse(jwtService.generateToken(savedUser), userDTO);
//    }

//    @Override
//    public UserDTO createUser(UserDTO userDTO) {
//        if (!userRepository.existsByEmail(userDTO.email())) {
//            User user = customModelMapper.reverse(userDTO);
//            User savedUser = userRepository.save(user);
//            EmailDetails emailDetails = EmailDetails.builder()
//                    .recipient(savedUser.getEmail())
//                    .subject("Account Creation")
//                    .messageBody("Congratulations! You have successfully created your account!")
//                    .build();
//            emailService.sendEmailAlert(emailDetails);
//            return customModelMapper.apply(userRepository.save(savedUser));
//        }
//        throw new DuplicateEntryException("Email already exists");
//    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with this id " + id + " not found"));
        return customModelMapper.apply(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(customModelMapper::apply).toList();
    }

//    @Override
//    public UserDTO updateUser(Long userId, UserDTO userDTO) {
//        // Check for duplicate email entries
//        if (isDuplicate(userDTO)) {
//            throw new DuplicateEntryException(userDTO.email());
//        }
//
//        // Fetch existing user from the repository
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with this id: " + userId));
//
//        // Map updated values from UserDTO to User entity
//        User updatedValues = customModelMapper.reverse(userDTO);
//
//        // Copy properties from updatedValues to existingUser, excluding id and password
//        BeanUtils.copyProperties(updatedValues, existingUser, "id", "password"); // This line copies properties from updatedValues (which contains the immutable list) to existingUser.
//
//        // Save the updated user and return the UserDTO representation
//        return customModelMapper.apply(userRepository.save(existingUser));
//
//    }


//    @Override
//    public UserDTO updateUser(Long userId, UserDTO userDTO) {
//        // Check for duplicate email entries
//        if (isDuplicate(userDTO)) {
//            throw new DuplicateEntryException(userDTO.email());
//        }
//
//        // Fetch existing user from the repository
//        User existingUser = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException("User not found with this id: " + userId));
//
//        // Map updated values from UserDTO to User entity
//        User updatedValues = customModelMapper.reverse(userDTO);
//
//        // Copy properties from updatedValues to existingUser, excluding id, password, and addressList
//        BeanUtils.copyProperties(updatedValues, existingUser, "id", "password", "addressList");
//
//        // Update the address list manually
//        existingUser.getAddressList().clear();
//        existingUser.getAddressList().addAll(updatedValues.getAddressList());
//
//        // Save the updated user and return the UserDTO representation
//        return customModelMapper.apply(userRepository.save(existingUser));
//    }


    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        // Check for duplicate email entries
        if (isDuplicate(userDTO)) {
            throw new DuplicateEntryException(userDTO.email());
        }

        // Fetch existing user from the repository
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with this id: " + userId));

        // Map updated values from UserDTO to User entity
        User updatedValues = customModelMapper.reverse(userDTO);

        // Copy properties from updatedValues to existingUser, excluding id, password, addressList, and createdat
        BeanUtils.copyProperties(updatedValues, existingUser, "id", "password", "addressList", "createdat");

        // Update the address list manually
        existingUser.getAddressList().clear();
        existingUser.getAddressList().addAll(updatedValues.getAddressList());

        // Save the updated user and return the UserDTO representation
        return customModelMapper.apply(userRepository.save(existingUser));
    }


    public boolean verifyUser(Long userId, String firstName, String lastName, Long phoneNumber) {
        isCustomerExists(userId);
        return userRepository.existsByIdAndFirstNameAndLastNameAndDefaultPhoneNumber(userId, firstName, lastName, phoneNumber);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDTO getUserByEmail(String email) {
        return customModelMapper.apply(userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with the " + email + " not found")));
    }


    public ResponseEntity<?> register(User user) {

        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setRole(Role.ADMIN);
            User savedUser = userRepository.save(user);
            System.out.println("User saved to the database");

            EmailDetails emailDetails = EmailDetails.builder()
                    .recipient(savedUser.getEmail())
                    .subject("Account Creation")
                    .messageBody("Congratulations! You have successfully created your account!")
                    .build();
            emailService.sendEmailAlert(emailDetails);

            System.out.println("Password : " + user.getPassword());

            String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole().name());
            System.out.println(token);
            System.out.println(jwtService.getRoleFromToken(token));
            return ResponseEntity.ok(new AuthenticationResponse(token, customModelMapper.apply(savedUser)));
        } else {

            throw new DuplicateEntryException("Email already exists");
        }
    }


    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with this email : " + email));

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (authentication.isAuthenticated()) {

            //Extract role from user entity
            String role = user.getRole().name();

            return jwtService.generateToken(email, role);
        }
        return "fail";
    }

    private boolean isCustomerExists(Long id) {
        return userRepository.existsById(id);
    }

    private boolean isDuplicate(UserDTO userDTO) {
        if (userDTO == null) {
            throw new RuntimeException("UserDTO is null");
        }
        if (Objects.nonNull(userDTO.defaultPhoneNumber()) && userRepository.existsByDefaultPhoneNumber(userDTO.defaultPhoneNumber())) {
            return true;
        }
        return Objects.nonNull(userDTO.email()) && userRepository.existsByEmail(userDTO.email());
    }

}

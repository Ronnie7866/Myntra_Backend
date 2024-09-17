package com.backend.ecommerce.implementation;

import com.backend.ecommerce.authentication.AuthenticationRequest;
import com.backend.ecommerce.authentication.AuthenticationResponse;
import com.backend.ecommerce.authentication.RegisterRequest;
import com.backend.ecommerce.mapper.UserMapper;
import com.backend.ecommerce.dto.EmailDetails;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.enums.Role;
import com.backend.ecommerce.exception.DuplicateEntryException;
import com.backend.ecommerce.records.UserDTO;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper customModelMapper;
    private final EmailServiceImplementation emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthenticationResponse register(RegisterRequest request) {

        // Check if mail already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEntryException("Email already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        // Send email notification to the user
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations! You have successfully created your account!")
                .build();
        emailService.sendEmailAlert(emailDetails);

        // Generate JWT token
        String token = jwtService.generateToken(savedUser.getEmail(), savedUser.getRole().name());

        UserDTO userDTO = customModelMapper.apply(savedUser);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setUserDTO(userDTO);
        System.out.println("Token: " + token);
        System.out.println("User: " + userDTO);
        return authenticationResponse;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        doAuthenticate(request.getEmail(), request.getPassword());
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new DuplicateEntryException("Email already exists"));
        var jwtToken = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return AuthenticationResponse.builder().token(jwtToken)
                .userDTO(customModelMapper.apply(user)) // convert user into userDTO
                .build();
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Password");
        }
    }
}

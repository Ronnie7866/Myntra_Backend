package com.backend.ecommerce.controllers;

import com.backend.ecommerce.authentication.AuthenticationRequest;
import com.backend.ecommerce.authentication.AuthenticationResponse;
import com.backend.ecommerce.authentication.RegisterRequest;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.implementation.AuthenticationService;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.security.JWTService;
import com.backend.ecommerce.utility.JwtBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtBlacklistService jwtBlacklistService;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtBlacklistService jwtBlacklistService, UserRepository userRepository) {
        this.authenticationService = authenticationService;
        this.jwtBlacklistService = jwtBlacklistService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse registered = authenticationService.register(request);
        return new ResponseEntity<>(registered, HttpStatus.CREATED);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String autheader = request.getHeader("Authorization");
        if (autheader != null && autheader.startsWith("Bearer ")) {
            String token = autheader.substring(7);
            jwtBlacklistService.addBlacklist(token);
        }
        return new ResponseEntity<>("Logout Successful", HttpStatus.OK);
    }

//    @GetMapping("/user-info")
//    public String saveUser(OAuth2AuthenticationToken authentication) {
//        System.out.println("Method called");
//        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
//
//        attributes.forEach((key, value) -> {
//            System.out.println("Key: " + key + " Value: " + value);
//        });
//
//        String googleId = attributes.get("sub").toString();
//        String email = attributes.get("email").toString();
//        String name = attributes.get("name").toString();
//
//        // check if user already exists
//        User user = userRepository.findByGoogleId(googleId);
//        if (user == null) {
//            User newUser = new User();
//            newUser.setEmail(email);
//            newUser.setFirstName(name);
//            newUser.setLastName(name);
//            newUser.setGoogleId(googleId);
//            userRepository.save(newUser);
//        }
//        return "User saved successfully";
//    }
}

package com.backend.ecommerce.authentication;

import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.enums.Role;
import com.backend.ecommerce.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Autowired
    public OAuthAuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User googleUser = (DefaultOAuth2User) authentication.getPrincipal();

        String firstName = Objects.requireNonNull(googleUser.getAttribute("given_name")).toString();
        String lastName = Objects.requireNonNull(googleUser.getAttribute("family_name")).toString();
        String email = Objects.requireNonNull(googleUser.getAttribute("email")).toString();

        // create user and save in database
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        Random random = new Random();
        Long userId = random.nextLong() & Long.MAX_VALUE;
        user.setId(userId);
        user.setPassword("password");
        user.setRole(Role.USER);

        User DBUser = userRepository.findByEmail(email).orElse(null);

        if (DBUser == null) {
            userRepository.save(user);
            System.out.println("User saved Successfully");
        }
        System.out.println("User exists ");
        new DefaultRedirectStrategy().sendRedirect(request, response, "http://localhost:5173/");
    }
}

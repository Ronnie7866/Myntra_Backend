package com.backend.ecommerce.implementation;

import com.backend.ecommerce.dto.EmailDetails;
import com.backend.ecommerce.entity.ForgotPassword;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.exception.PasswordDidNotMatchException;
import com.backend.ecommerce.exception.UserNotFoundException;
import com.backend.ecommerce.repository.EmailService;
import com.backend.ecommerce.repository.ForgotPasswordRepository;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.service.ForgotPasswordService;
import com.backend.ecommerce.records.ChangePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
public class ForgotPasswordServiceImplementation implements ForgotPasswordService {

    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public ForgotPasswordServiceImplementation(EmailService emailService, UserRepository userRepository, ForgotPasswordRepository forgotPasswordRepository ) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @Override
    public String verifyEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found in the database with this email id: " + email));
        int otp = generateOTP();
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(email)
                .messageBody("This is the OTP for your Forgot Password request :" + otp)
                .subject("OTP for forgot password request")
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .user(user)
                .build();

        emailService.sendEmailAlert(emailDetails);
        forgotPasswordRepository.save(forgotPassword);
        return "Email sent for verification";
    }

    @Override
    public String verifyOTP(Integer otp, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found in the database with this email id: " + email));
        ForgotPassword forgotPassword = forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(() -> new RuntimeException("Invalid OTP for email : " + email));

        if (forgotPassword.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(forgotPassword.getId());
            return "OTP has expired";
        }
        return "OTP verified";
    }

    @Override
    public String changePasswordHandler(ChangePassword changePassword, String email) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            throw new PasswordDidNotMatchException("The entered password is not matching ");
        }
        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email, encodedPassword);

        emailService.sendEmailAlert(EmailDetails.builder()
                        .subject("Password Changed")
                        .messageBody("Your password has successfully been changed")
                        .recipient(email)
                .build());
        return "Password has been changed";
    }

    private Integer generateOTP() {
        Random random = new Random();
        return random.nextInt(100000, 999999);
    }
}

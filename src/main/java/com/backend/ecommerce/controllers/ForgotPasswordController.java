package com.backend.ecommerce.controllers;

import com.backend.ecommerce.implementation.ForgotPasswordServiceImplementation;
import com.backend.ecommerce.records.ChangePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final ForgotPasswordServiceImplementation forgotPasswordService;

    @Autowired
    public ForgotPasswordController(ForgotPasswordServiceImplementation forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        String s = forgotPasswordService.verifyEmail(email);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/verifyOTP/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email) {
        String s = forgotPasswordService.verifyOTP(otp, email);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {
        String s = forgotPasswordService.changePasswordHandler(changePassword, email);
        return ResponseEntity.ok(s);
    }
}

package com.backend.ecommerce.service;

import com.backend.ecommerce.records.ChangePassword;

public interface ForgotPasswordService {
    String verifyEmail(String email);
    String verifyOTP(Integer otp, String email);
    String changePasswordHandler(ChangePassword changePassword, String email);
}

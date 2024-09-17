package com.backend.ecommerce.repository;

import com.backend.ecommerce.dto.EmailDetails;

public interface EmailService {

    void sendEmailAlert(EmailDetails emailDetails);
}

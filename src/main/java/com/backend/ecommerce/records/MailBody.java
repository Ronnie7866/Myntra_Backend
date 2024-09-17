package com.backend.ecommerce.records;

public record MailBody(String recipient,String messageBody, String subject, String attachment) {
}

package com.bank.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class CustomerIdGenerator {
    final SecureRandom random = new SecureRandom();

    public String generateCustomerId() {
        int number = random.nextInt(1000000); // 0 to 999999
        return String.format("%06d", number);
    }


}

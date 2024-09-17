package com.backend.ecommerce.utility;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtBlacklistService {
    private final Set<String> blacklist = new HashSet<>();

    public void addBlacklist(String token) {
        blacklist.add(token);
    }
    public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }
}

package ait.cohort46.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBlackList {
    private final ConcurrentHashMap<String, Date> blacklist = new ConcurrentHashMap<>();

    public void addToBlackList(String token, Date expirationDate) {
        blacklist.put(token, expirationDate);
        System.out.println("Current blacklisted tokens: " + blacklist);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.containsKey(token);
    }
}

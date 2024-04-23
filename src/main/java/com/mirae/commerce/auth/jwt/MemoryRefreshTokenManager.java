package com.mirae.commerce.auth.jwt;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Primary
@Component
public class MemoryRefreshTokenManager implements RefreshTokenManager {
    HashMap<String, String> store = new HashMap<>();

    @Override
    public void putRefreshToken(String username, String uuid) {
        store.put(username, uuid);
    }

    @Override
    public String getRefreshToken(String username) {
        return store.get(username);
    }

    @Override
    public void removeRefreshToken(String username) {
        store.remove(username);
    }
}

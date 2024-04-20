package com.mirae.commerce.member.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class MemoryEmailConfirmationManager implements EmailConfirmationManager {
    HashMap<UUID, String> store = new HashMap<>();

    @Override
    public void putConfirmation(UUID uuid, String username) {
        store.put(uuid, username);
    }

    @Override
    public String getConfirmation(UUID uuid) {
        return store.get(uuid);
    }

    @Override
    public void removeConfirmation(UUID uuid) {
        store.remove(uuid);
    }
}

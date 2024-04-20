package com.mirae.commerce.common.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;

@Component
public class RSAKeyManager {
    private static final String PUBLIC_KEY_FILE = "etc/keys/publicKey.pem";
    private static final String PRIVATE_KEY_FILE = "etc/keys/privateKey.pem";

    public KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePublicKey(PublicKey publicKey) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE))) {
            oos.writeObject(publicKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void savePrivateKey(PrivateKey privateKey) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE))) {
            oos.writeObject(privateKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PublicKey loadPublicKey() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE))) {
            return (PublicKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey loadPrivateKey() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE))) {
            return (PrivateKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

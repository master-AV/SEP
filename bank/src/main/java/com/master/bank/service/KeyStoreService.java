package com.master.bank.service;

import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

import static com.master.bank.utils.Constants.*;

@Service
public class KeyStoreService {

    public void createKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());

            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                SecureRandom secureRandom = new SecureRandom();
                keyGen.init(secureRandom);
                SecretKey secretKey = keyGen.generateKey();

                KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(secretKey);
                KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray());

                keyStore.setEntry(KEY_ALIAS, keyEntry, keyPassword);

                FileOutputStream fos = new FileOutputStream(KEYSTORE_FILENAME);
                keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
                fos.close();
            } else {
                System.out.println("Key already exists in keystore.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Example usage
    public static void main(String[] args) {
        KeyStoreService keyStoreService = new KeyStoreService();
        keyStoreService.createKeyStore();
    }
}

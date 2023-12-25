package com.master.bank.service;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Base64;

import static com.master.bank.utils.Constants.*;

@Service
public class CryptoService {

    private SecretKey secretKey;

    public CryptoService() {
        this.secretKey = loadKey();
    }

    public static SecretKey loadKey() {
        try {
            FileInputStream fis = new FileInputStream(KEYSTORE_FILENAME);
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());

            if (keyStore.containsAlias(KEY_ALIAS)) {
                return (SecretKey) keyStore.getKey(KEY_ALIAS, KEY_PASSWORD.toCharArray());
            } else {
                System.err.println("Key not found in the keystore.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    private SecretKey loadOrGenerateKey() {
//        try {
//            KeyStore keyStore = KeyStore.getInstance("JCEKS");
//            keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
//
//            if (keyStore.containsAlias(KEY_ALIAS)) {
//                KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS,
//                        new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray()));
//                return keyEntry.getSecretKey();
//            } else {
//                SecureRandom random = new SecureRandom();
//                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//                keyGen.init(random);
//                SecretKey secretKey = keyGen.generateKey();
//
//                KeyStore.SecretKeyEntry keyEntry = new KeyStore.SecretKeyEntry(secretKey);
//                keyStore.setEntry(KEY_ALIAS, keyEntry, new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray()));
//                // Save the keystore
//                // Replace "keystoreFileName" with your desired file name
////                keyStore.store(new java.io.FileOutputStream("keystoreFileName"), KEYSTORE_PASSWORD.toCharArray());
//
//                return secretKey;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Error loading or generating key", e);
//        }
//    }

    // Encryption using the loaded/generated key
    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decryption using the loaded/generated key
    public String decrypt(String encryptedText) {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


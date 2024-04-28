package com.secureftpdbconnection.SecureDbConnection.Services;

import com.secureftpdbconnection.SecureDbConnection.Models.FtpSettings;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {
    private final KeyGenerator keyGenerator;
    private final SecretKey secretKey;
    private final Cipher cipher;

    public EncryptionService() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        this.keyGenerator = KeyGenerator.getInstance("AES");
        this.keyGenerator.init(128); // AES key size in number of bits
        this.secretKey = this.getConstantKey();
        this.cipher = Cipher.getInstance("AES");
    }


    // Method to return a constant SecretKey
    private SecretKey getConstantKey() {
        var ENCODED_KEY = System.getenv("JavaEncryptionKey");
        // Decode the Base64 encoded key
        byte[] decodedKey = Base64.getDecoder().decode(ENCODED_KEY);
        // Rebuild the key using SecretKeySpec
        return new SecretKeySpec(decodedKey, "AES");
    }

    public String encrypt(String plainText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

        // Encode bytes to base64 to get a string
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    public FtpSettings getEncryptSettings(FtpSettings settings) {
        try {
            settings.setUserName(this.encrypt(settings.getUserName()));
            settings.setHost(this.encrypt(settings.getHost()));
            settings.setPassword(this.encrypt(settings.getPassword()));
            settings.setToken(this.encrypt(settings.getToken()));
            return settings;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public FtpSettings getDecryptSettings(FtpSettings settings) {
        try {
            settings.setUserName(this.decrypt(settings.getUserName()));
            settings.setHost(this.decrypt(settings.getHost()));
            settings.setPassword(this.decrypt(settings.getPassword()));
            settings.setToken(this.decrypt(settings.getToken()));
            return settings;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}

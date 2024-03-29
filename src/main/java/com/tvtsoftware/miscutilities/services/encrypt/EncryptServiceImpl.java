package com.tvtsoftware.miscutilities.services.encrypt;

import com.tvtsoftware.miscutilities.enums.EncryptConstants;
import com.tvtsoftware.miscutilities.exceptions.FileSavingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.UUID;

@Component
public class EncryptServiceImpl implements EncryptService {
    public static final byte[] EMPTY_BYTES = new byte[0];
    Logger LOGGER = LoggerFactory.getLogger(EncryptServiceImpl.class);

    @Override
    public byte[] encrypt(byte[] bytesToEncrypt, byte[] keyAsBytes, String algorithm) {
        final Cipher eCipher;
        try {
            eCipher = Cipher.getInstance(algorithm);
            eCipher.init(Cipher.ENCRYPT_MODE, createSecretKey(keyAsBytes, algorithm));
            return eCipher.doFinal(bytesToEncrypt);
        } catch (Exception e) {
            LOGGER.error("Error occurred during encrypting data" + e.getMessage());
            return EMPTY_BYTES;
        }
    }

    @Override
    public byte[] decrypt(byte[] byteToDecrypt, byte[] keyAsBytes, String algorithm) {
        try {
            final Cipher dCipher = Cipher.getInstance(algorithm);
            dCipher.init(Cipher.DECRYPT_MODE, createSecretKey(keyAsBytes, algorithm));
            return dCipher.doFinal(byteToDecrypt);
        } catch (Exception e) {
            LOGGER.error("Error occurred during encrypting data" + e.getMessage());
            return EMPTY_BYTES;
        }
    }

    @Override
    public String encryptFile(byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType, String algorithm) throws FileSavingException {
        switch (outputType) {
            case FILE: {
                return saveAsTempFile(encrypt(bytesOfFile, keyfileBytes, algorithm));
            }
            case HEX: {
                return Hex.encodeHexString(encrypt(bytesOfFile, keyfileBytes, algorithm));
            }
            default: {
                return Base64.encodeBase64String(encrypt(bytesOfFile, keyfileBytes, algorithm));
            }
        }
    }

    @Override
    public String encryptText(String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset, String algorithm) throws UnsupportedEncodingException, FileSavingException {
        if (charset == null || charset.isEmpty()) {
            charset = "UTF-8";
        }
        switch (outputType) {
            case FILE: {
                return saveAsTempFile(encrypt(data.getBytes(charset), keyfileBytes, algorithm));
            }
            case HEX: {
                return Hex.encodeHexString(encrypt(data.getBytes(charset), keyfileBytes, algorithm));
            }
            case BASE64: {
                return Base64.encodeBase64String(encrypt(data.getBytes(charset), keyfileBytes, algorithm));
            }
            default: {
                return new String(encrypt(data.getBytes(charset), keyfileBytes, algorithm));
            }
        }
    }

    @Override
    public String decryptFile(EncryptConstants.DecodeSupport decodeSupport, byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType, String algorithm) throws DecoderException, FileSavingException {

        byte[] decodedData;
        // Check if we need perform decode.
        if (decodeSupport == EncryptConstants.DecodeSupport.BASE_64) {
            decodedData = Base64.decodeBase64(bytesOfFile);
        } else if (decodeSupport == EncryptConstants.DecodeSupport.HEX) {
            decodedData = Hex.decodeHex(new String(bytesOfFile));
        } else {
            decodedData = bytesOfFile;
        }

        // Checking for output type
        if (outputType == EncryptConstants.Output.FILE) {
            return saveAsTempFile(decrypt(decodedData, keyfileBytes, algorithm));
        }
        return new String(decrypt(decodedData, keyfileBytes, algorithm));
    }

    @Override
    public String decryptText(EncryptConstants.DecodeSupport encodeSupport, String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset, String algorithm) throws DecoderException, UnsupportedEncodingException, FileSavingException {

        byte[] decodedData = new byte[0];
        // Check if we need perform decode.
        if (encodeSupport == EncryptConstants.DecodeSupport.BASE_64) {
            decodedData = Base64.decodeBase64(data);
        } else if (encodeSupport == EncryptConstants.DecodeSupport.HEX) {
            decodedData = Hex.decodeHex(data);
        }

        // Checking for output type
        if (outputType == EncryptConstants.Output.FILE) {
            return saveAsTempFile(decrypt(decodedData, keyfileBytes, algorithm));
        }
        return new String(decrypt(decodedData, keyfileBytes, algorithm), charset);
    }

    /**
     * Generate a Triple DES keyfile.
     *
     * @return path
     */
    @Override
    public String generateKeyFile(String algorithm) {
        String fullPath = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            if ("Blowfish".equals(algorithm)) {
                keyGenerator.init(128);
            }
            SecretKey desEdeKey = keyGenerator.generateKey();

            File tempKeyFile = File.createTempFile(UUID.randomUUID().toString(), ".key");

            byte[] keyBytes = desEdeKey.getEncoded();
            String keyString = new String(keyBytes);

            // Staring write key file to temporary directory
            FileWriter writer = new FileWriter(tempKeyFile);
            writer.write(keyString, 0, keyString.length());
            writer.close(); // Close writer resources

            fullPath = tempKeyFile.getAbsolutePath();
        } catch (Exception e) {
            LOGGER.error("Error occurred during generate key file. message {}", Arrays.toString(e.getStackTrace()));
        }
        return fullPath;
    }

    /**
     * Convert the raw bytes of a key back to a SecretKey object.
     * This does not support for base64 encoded key-file or anything else.
     * Please handle decrypt if key-file are encoded to retrieve origin byte data of origin key-file.
     *
     * @param keyAsBytes bytes of key file
     * @return Generated @{@link SecretKey}
     */
    private SecretKey createSecretKey(byte[] keyAsBytes, String algorithms) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey secretKey = null;
        KeySpec keySpec = null;
        switch (algorithms.toUpperCase()) {
            case "DES":
                keySpec = new DESKeySpec(keyAsBytes);
                secretKey = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);
                break;
            case "AES":
                secretKey = new SecretKeySpec(keyAsBytes, "AES");
                break;
            case "DESEDE":
                keySpec = new DESedeKeySpec(keyAsBytes);
                secretKey = SecretKeyFactory.getInstance("DESede").generateSecret(keySpec);
                break;
            case "BLOWFISH":
                secretKey = new SecretKeySpec(keyAsBytes, "Blowfish");
                break;
        }
        return secretKey;
    }

    @Override
    public String saveAsTempFile(byte[] bytesToSaved) throws FileSavingException {
        String fullPath;
        try {
            File tempKeyFile = File.createTempFile(UUID.randomUUID().toString(), null);
            OutputStream outputStream = new FileOutputStream(tempKeyFile);
            outputStream.write(bytesToSaved);
            outputStream.close(); // Close writer resources
            fullPath = tempKeyFile.getAbsolutePath();
        } catch (Exception e) {
            LOGGER.error("Error occurred during saving temporary key file. message {}", Arrays.toString(e.getStackTrace()));
            throw new FileSavingException(e.getMessage(), e);
        }

        return fullPath;
    }
}

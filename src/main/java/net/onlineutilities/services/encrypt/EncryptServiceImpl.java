package net.onlineutilities.services.encrypt;

import net.onlineutilities.enums.EncryptOutputType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.util.Arrays;
import java.util.UUID;

@Component
public class EncryptServiceImpl implements EncryptService {

    @Override
    public byte[] encrypt(byte[] bytesToEncrypt, byte[] keyAsBytes) {
        Cipher ecipher = null;
        try {
            ecipher = Cipher.getInstance("DESede");
            ecipher.init(Cipher.ENCRYPT_MODE, createSecretKey(keyAsBytes));
            byte[] enc = ecipher.doFinal(bytesToEncrypt);
            return enc;
        } catch (Exception e) {
            // TODO implement logic for exceptions and log to the system.
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public byte[] decrypt(byte[] byteToDecrypt, byte[] keyAsBytes) {
        try {
            Cipher ecipher = Cipher.getInstance("DESede");
            ecipher.init(Cipher.DECRYPT_MODE, createSecretKey(keyAsBytes));
            // Encrypt
            return ecipher.doFinal(byteToDecrypt);
        } catch (Exception e) {
            // TODO handling exception and logging
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Generate a Triple DES keyfile.
     *
     * @return path
     */
    @Override
    public String generateKeyFile() {
        String fullPath = null;
        try {
            KeyGenerator desEdeGen = KeyGenerator.getInstance("DESede"); // Triple DES
            SecretKey desEdeKey = desEdeGen.generateKey();

            File tempKeyFile = File.createTempFile("tripledes", Long.toString(System.nanoTime()));

            byte[] keyBytes = desEdeKey.getEncoded();
            String keyString = new String(keyBytes);

            // Staring write key file to temporary directory
            FileWriter writer = new FileWriter(tempKeyFile);
            writer.write(keyString, 0, keyString.length());
            writer.close(); // Close writer resources

            fullPath = tempKeyFile.getAbsolutePath();
        } catch (Exception e) {
            // TODO implement log message here
        }
        return fullPath;
    }

    @Override
    public String encryptFile(byte[] originFileAsByte, byte[] keyAsBytes, EncryptOutputType outputTyp) {
        switch (outputTyp) {
            case HEX:{
                return encryptThenDoHex(Arrays.toString(originFileAsByte), keyAsBytes);
            }
            case BASE64:{
                return encryptThenDoBase64(Arrays.toString(originFileAsByte), keyAsBytes);
            }
            default:{
                return encryptThenSaveToFile(originFileAsByte, keyAsBytes);
            }
        }
    }

    @Override
    public String textBasedEncrypt(String inputData, byte[] keyfileBytes, EncryptOutputType outputTyp) {
        switch (outputTyp) {
            case HEX:{
                return encryptThenDoHex(inputData, keyfileBytes);
            }
            case BASE64:{
                return encryptThenDoBase64(inputData, keyfileBytes);
            }
            default:{
                return encryptThenSaveToFile(inputData, keyfileBytes);
            }
        }
    }

    /**
     *
     * @param inputData bytes of file to encrypt
     * @param keyfileBytes bytes of key file
     * @param outputType output type, It should be {@link EncryptOutputType}
     * @return String value or path file based on output type
     */
    @Override
    public String decryptFile(byte[] inputData, byte[] keyfileBytes, EncryptOutputType outputType) {
        if (outputType == EncryptOutputType.FILE) {
            return decryptThenSaveToFile(inputData, keyfileBytes);
        }
        return Arrays.toString(decrypt(inputData, keyfileBytes));
    }

    @Override
    public String decryptTextBased(String data, byte[] bytes, EncryptOutputType outputType) throws UnsupportedEncodingException {
        if (outputType == EncryptOutputType.FILE) {
            return decryptThenSaveToFile(data.getBytes("UTF-8"), bytes);
        }
        return Arrays.toString(decrypt(data.getBytes("UTF-8"), bytes));
    }

    /**
     * Convert the raw bytes of a key back to a SecretKey object.
     * This does not support for base64 encoded key-file or anything else.
     * Please handle decrypt if key-file are encoded to retrieve origin byte data of origin key-file.
     *
     * @param keyAsBytes bytes of key file
     * @return Generated @{@link SecretKey}
     */
    private SecretKey createSecretKey(byte[] keyAsBytes) {
        SecretKey secretKey = null;
        try {
            SecretKeyFactory desEdeFactory = SecretKeyFactory.getInstance("DESede");
            DESedeKeySpec keyspec = new DESedeKeySpec(keyAsBytes);
            secretKey = desEdeFactory.generateSecret(keyspec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    @Override
    public String encryptThenDoBase64(String textToEncrypt, byte[] keyFileAsByte) {
        try {
            byte[] bytesFromCharset = textToEncrypt.getBytes("UTF-8");
            byte[] encrypted = encrypt(bytesFromCharset, keyFileAsByte);
            return new String(Base64.encodeBase64(encrypted));
        } catch (Exception e) {
            // TODO handle exception
            return null;
        }
    }

    @Override
    public String encryptThenDoHex(String textToEncrypt, byte[] keyFileAsByte) {
        try {
            byte[] bytesFromCharset = textToEncrypt.getBytes("UTF-8");
            byte[] encrypted = encrypt(bytesFromCharset, keyFileAsByte);
            return new String(Hex.encodeHex(encrypted));
        } catch (Exception e) {
            // TODO handle exception
            return null;
        }
    }

    @Override
    public String encryptThenSaveToFile(String textToEncrypt, byte[] keyFileAsByte) {
        String fullPath = null;
        try{
            File tempKeyFile = File.createTempFile("tripledes-encrypted-data-", Long.toString(System.nanoTime()));

            byte[] keyBytes = encrypt(textToEncrypt.getBytes("UTF-8"), keyFileAsByte);
            String keyString = new String(keyBytes);

            // Staring write encrypted data to temporary file
            FileWriter writer = new FileWriter(tempKeyFile);
            writer.write(keyString, 0, keyString.length());
            writer.close(); // Close writer resources

            fullPath = tempKeyFile.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            // TODO
            return null;
        }

        return fullPath;
    }

    @Override
    public String encryptThenSaveToFile(byte[] file, byte[] keyFileAsByte) {
        String fullPath = null;
        try{
            File tempKeyFile = File.createTempFile("tripledes-encrypted-data-", Long.toString(System.nanoTime()));

            byte[] keyBytes = encrypt(file, keyFileAsByte);
            String keyString = new String(keyBytes);

            // Staring write encrypted data to temporary file
            FileWriter writer = new FileWriter(tempKeyFile);
            writer.write(keyString, 0, keyString.length());
            writer.close(); // Close writer resources

            fullPath = tempKeyFile.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            // TODO
            return null;
        }

        return fullPath;
    }

    @Override
    public String base64EncryptThenDecrypt(String base64String, byte[] keyAsBytes) throws UnsupportedEncodingException {
        // Decode base64 to get bytes
        byte[] dec = Base64.decodeBase64(base64String.getBytes());
        return new String(decrypt(dec, keyAsBytes), "UTF-8");
    }


    @Override
    public String decryptThenSaveToFile(byte[] file, byte[] keyFileAsByte) {
        String fullPath = null;
        try{
            File tempKeyFile = File.createTempFile("tripledes-decrypted-data-", UUID.randomUUID().toString());

            byte[] keyBytes = decrypt(file, keyFileAsByte);

            // Staring write encrypted data to temporary file
            OutputStream outputStream = new FileOutputStream(tempKeyFile);
            outputStream.write(keyBytes);
            outputStream.close(); // Close writer resources

            fullPath = tempKeyFile.getAbsolutePath();
        }catch (Exception e){
            return null;
        }

        return fullPath;
    }
}

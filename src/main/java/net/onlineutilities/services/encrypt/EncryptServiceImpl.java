package net.onlineutilities.services.encrypt;

import net.onlineutilities.enums.EncryptConstants;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.util.UUID;

@Component
public class EncryptServiceImpl implements EncryptService {

    @Override
    public byte[] encrypt(byte[] bytesToEncrypt, byte[] keyAsBytes) {
        Cipher ecipher;
        try {
            ecipher = Cipher.getInstance("DESede");
            ecipher.init(Cipher.ENCRYPT_MODE, createSecretKey(keyAsBytes));
            return ecipher.doFinal(bytesToEncrypt);
        } catch (Exception e) {
            // TODO implement logic for exceptions and log to the system.
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public byte[] decrypt(byte[] byteToDecrypt, byte[] keyAsBytes) {
        try {
            Cipher dcipher = Cipher.getInstance("DESede");
            dcipher.init(Cipher.DECRYPT_MODE, createSecretKey(keyAsBytes));
            return dcipher.doFinal(byteToDecrypt);
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public String encryptFile(byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType) {
        switch (outputType){
            case FILE:{
                return saveAsTempFile(encrypt(bytesOfFile, keyfileBytes));
            }case HEX:{
                return Hex.encodeHexString(encrypt(bytesOfFile, keyfileBytes));
            } default:{
                return Base64.encodeBase64String(encrypt(bytesOfFile, keyfileBytes));
            }
        }
    }

    @Override
    public String encryptText(String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset) throws UnsupportedEncodingException {
        if (charset == null || charset.isEmpty()){
            charset = "UTF-8";
        }
        switch (outputType){
            case FILE:{
                return saveAsTempFile(encrypt(data.getBytes(charset), keyfileBytes));
            }case HEX:{
                return Hex.encodeHexString(encrypt(data.getBytes(charset), keyfileBytes));
            } default:{
                return Base64.encodeBase64String(encrypt(data.getBytes(charset), keyfileBytes));
            }
        }
    }

    @Override
    public String decryptFile(EncryptConstants.EncodeSupport encodeSupport, byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType) throws DecoderException {

        byte[] decodedData = new byte[0];
        // Check if we need perform decode.
        if(encodeSupport == EncryptConstants.EncodeSupport.BASE_64){
            decodedData = Base64.decodeBase64(bytesOfFile);
        }else if (encodeSupport == EncryptConstants.EncodeSupport.HEX){
            decodedData = Hex.decodeHex(new String(bytesOfFile));
        }else {
            decodedData = bytesOfFile;
        }

        // Checking for output type
        if (outputType == EncryptConstants.Output.FILE) {
            return saveAsTempFile(decrypt(decodedData, keyfileBytes));
        }
        return new String(decrypt(decodedData, keyfileBytes));
    }

    @Override
    public String decryptText(EncryptConstants.EncodeSupport encodeSupport, String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset) throws DecoderException, UnsupportedEncodingException {

        byte[] decodedData = new byte[0];
        // Check if we need perform decode.
        if(encodeSupport == EncryptConstants.EncodeSupport.BASE_64){
            decodedData = Base64.decodeBase64(data);
        }else if (encodeSupport == EncryptConstants.EncodeSupport.HEX){
            decodedData = Hex.decodeHex(data);
        }

        // Checking for output type
        if (outputType == EncryptConstants.Output.FILE) {
            return saveAsTempFile(decrypt(decodedData, keyfileBytes));
        }
        return new String(decrypt(decodedData, keyfileBytes), charset);
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

            File tempKeyFile = File.createTempFile(UUID.randomUUID().toString(), ".deskey");

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
    public String saveAsTempFile(byte[] bytesToSaved) {
        String fullPath;
        try{
            File tempKeyFile = File.createTempFile(UUID.randomUUID().toString(), null);
            OutputStream outputStream = new FileOutputStream(tempKeyFile);
            outputStream.write(bytesToSaved);
            outputStream.close(); // Close writer resources
            fullPath = tempKeyFile.getAbsolutePath();
        }catch (Exception e){
            return null;
        }

        return fullPath;
    }
}

package net.onlineutilities.services.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
public class EncryptServiceImpl implements EncryptService {
    @Override
    public String encrypt(String stringToEncrypt, byte[] keyAsBytes) {
        Cipher ecipher = null;
        String originData = null;
        try {
            ecipher = Cipher.getInstance("DESede");
            ecipher.init(Cipher.ENCRYPT_MODE, createSecretKey(keyAsBytes));
            byte[] utf8 = stringToEncrypt.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            originData = new String(enc);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException noSuchPaddingException) {

        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return originData;
    }

    @Override
    public String decrypt(String stringToDecrypt, byte[] keyAsBytes) {
        Cipher ecipher;
        String originData = null;
        try {
            ecipher = Cipher.getInstance("DESede");
            ecipher.init(Cipher.DECRYPT_MODE, createSecretKey(keyAsBytes));
            byte[] utf8 = stringToDecrypt.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            originData = new String(enc);
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException noSuchPaddingException) {

        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return originData;
    }

    private SecretKey createSecretKey(byte[] keyAsBytes) {
        SecretKey secretKey = null;
        try {
            byte[] dec = Base64.decodeBase64(keyAsBytes);
            secretKey = new SecretKeySpec(dec, "DESede");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }
}

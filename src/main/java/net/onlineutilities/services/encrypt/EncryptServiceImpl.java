package net.onlineutilities.services.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

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
            byte[] enc = ecipher.doFinal(utf8);
            originData = new String(enc);
        } catch (Exception e) {
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

    /**
     * Generate a Triple DES keyfile.
     * @return path
     */
    @Override
    public String generateKeyFile() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        String fullPath = null;
        try {
            KeyGenerator desEdeGen = KeyGenerator.getInstance("DESede"); // Triple DES
            SecretKey desEdeKey = desEdeGen.generateKey();

            File tempKeyFile = File.createTempFile("tripledes", Long.toString(System.nanoTime()));

            byte[] keyBytes = desEdeKey.getEncoded();
            String keyString = new String(keyBytes);

            FileWriter writer = new FileWriter(tempKeyFile);
            writer.write(keyString, 0, keyString.length());
            writer.close();

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
     * @param keyAsBytes
     * @return
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
}

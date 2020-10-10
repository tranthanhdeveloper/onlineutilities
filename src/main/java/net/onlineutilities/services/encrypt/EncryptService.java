package net.onlineutilities.services.encrypt;

import net.onlineutilities.enums.EncryptConstants;
import org.apache.commons.codec.DecoderException;

import java.io.UnsupportedEncodingException;

public interface EncryptService {
	byte[] encrypt(byte[] originBytes, byte[] keyfileBytes, String algorithm);
	byte[] decrypt(byte[] originBytes, byte[] keyfileBytes, String algorithm);

	String encryptFile(byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType, String algorithm);
	String encryptText(String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset , String algorithm) throws UnsupportedEncodingException;

	String decryptFile(EncryptConstants.DecodeSupport encodeSupport, byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType , String algorithm) throws DecoderException;
	String decryptText(EncryptConstants.DecodeSupport encodeSupport, String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset, String algorithm) throws UnsupportedEncodingException, DecoderException;

	String generateKeyFile(String algorithm);

	String saveAsTempFile(byte[] bytesToSaved);
}

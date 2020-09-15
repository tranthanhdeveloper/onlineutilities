package net.onlineutilities.services.encrypt;

import net.onlineutilities.enums.EncryptConstants;
import org.apache.commons.codec.DecoderException;

import java.io.UnsupportedEncodingException;

public interface EncryptService {
	byte[] encrypt(byte[] originBytes, byte[] keyfileBytes);
	byte[] decrypt(byte[] originBytes, byte[] keyfileBytes);

	String encryptFile(byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType);
	String encryptText(String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset) throws UnsupportedEncodingException;

	String decryptFile(EncryptConstants.EncodeSupport encodeSupport, byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType) throws DecoderException;
	String decryptText(EncryptConstants.EncodeSupport encodeSupport, String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset) throws UnsupportedEncodingException, DecoderException;

	String generateKeyFile();

	String saveAsTempFile(byte[] bytesToSaved);
}

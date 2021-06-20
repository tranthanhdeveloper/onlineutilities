package net.onlineutilities.services.encrypt;

import net.onlineutilities.enums.EncryptConstants;
import net.onlineutilities.exceptions.FileSavingException;
import org.apache.commons.codec.DecoderException;

import java.io.UnsupportedEncodingException;

public interface EncryptService {
	byte[] encrypt(byte[] originBytes, byte[] keyfileBytes, String algorithm);
	byte[] decrypt(byte[] originBytes, byte[] keyfileBytes, String algorithm);

	String encryptFile(byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType, String algorithm) throws FileSavingException;
	String encryptText(String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset , String algorithm) throws UnsupportedEncodingException, FileSavingException;

	String decryptFile(EncryptConstants.DecodeSupport encodeSupport, byte[] bytesOfFile, byte[] keyfileBytes, EncryptConstants.Output outputType , String algorithm) throws DecoderException, FileSavingException;
	String decryptText(EncryptConstants.DecodeSupport encodeSupport, String data, byte[] keyfileBytes, EncryptConstants.Output outputType, String charset, String algorithm) throws UnsupportedEncodingException, DecoderException, FileSavingException;

	String generateKeyFile(String algorithm);

	String saveAsTempFile(byte[] bytesToSaved) throws FileSavingException;
}

package net.onlineutilities.services.encrypt;

import java.io.UnsupportedEncodingException;

public interface EncryptService {
	/**
	 * Triple DES encrypt
	 * @param bytesToEncrypt bytes of data to encrypt
	 * @param keyAsBytes bytes of keyfile
	 * @return
	 */
	byte[] encrypt(byte[] bytesToEncrypt, byte[] keyAsBytes);

	/**
	 * @param textToEncrypt Origin text data to be encrypted
	 * @param keyFileAsByte byte of keyfile
	 * @return A base64 encoded data
	 */
	String encryptThenDoBase64(String textToEncrypt, byte[] keyFileAsByte);

	/**
	 * @param textToEncrypt Origin text data to be encrypted
	 * @param keyFileAsByte byte of keyfile
	 * @return A HEX encoded data
	 */
	String encryptThenDoHex(String textToEncrypt, byte[] keyFileAsByte);

	/**
	 *
	 * @param byteToDecrypt
	 * @param keyAsBytes
	 * @return Origin String after decryption. It represents in UTF-8 charset.
	 */
	byte[] decrypt(byte[] byteToDecrypt, byte[] keyAsBytes);

	/**
	 * Do Base64 encrypt to byte[] then do Triple DES decryption
	 * @param base64String base64 encoded data
	 * @param keyAsBytes keyfile as byte[]
	 * @return Orgin string
	 */
	String base64EncryptThenDecrypt(String base64String, byte[] keyAsBytes) throws UnsupportedEncodingException;

	String encryptThenSaveToFile(String textToEncrypt, byte[] keyFileAsByte);

	/**
	 * Generate Triple DES keyfile
	 * @return
	 */
	String generateKeyFile();
}

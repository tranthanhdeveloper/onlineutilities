package net.onlineutilities.services.encrypt;

import net.onlineutilities.enums.EncryptOutputType;

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
	 * Encrypt a file, afterward return temporary file path
	 * @param file origin file
	 * @param keyFileAsByte byte of keyfile
	 * @return path file
	 */
	String encryptThenSaveToFile(byte[] file, byte[] keyFileAsByte);

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

	String encryptFile(byte[] originFileAsByte, byte[] keyAsBytes, EncryptOutputType outputTyp);

	/**
	 * Do Triple DES encrypt for text-based data
	 * @param inputData text-based data
	 * @param keyfileBytes keyfile as byte[]
	 * @param outputTyp output type response.
	 * @return
	 */
	String textBasedEncrypt(String inputData, byte[] keyfileBytes, EncryptOutputType outputTyp);

	/**
	 * Decrypt a file then return data correspondence to output type
	 * @param bytes bytes of file to encrypt
	 * @param bytes1 bytes of key file
	 * @param outputType output type, It should be {@link EncryptOutputType}
	 * @return Path file or encoded text value
	 */
	String decryptFile(byte[] bytes, byte[] bytes1, EncryptOutputType outputType);

	/**
	 * Decrypr a string then return data correspondence to output type
	 * @param data
	 * @param bytes
	 * @param outputType
	 * @return
	 */
	String decryptTextBased(String data, byte[] bytes, EncryptOutputType outputType) throws UnsupportedEncodingException;

	/**
	 * Decrypt data then write to file
	 * @param file origin file to decrypt
	 * @param keyFileAsByte bytes of keyfile
	 * @return full path of created file
	 */
	String decryptThenSaveToFile(byte[] file, byte[] keyFileAsByte);
}

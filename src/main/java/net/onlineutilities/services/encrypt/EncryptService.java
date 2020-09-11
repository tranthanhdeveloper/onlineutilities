package net.onlineutilities.services.encrypt;

public interface EncryptService {
	String encrypt(String data, byte[] keyAsBytes);
	String decrypt(String data, byte[] keyAsBytes);
	String generateKeyFile();
}

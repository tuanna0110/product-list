package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil {

	/**
	 * 文字例をSHA512で暗号化する
	 * 
	 * @param value
	 *            入力文字例
	 * @return String 暗号化した文字例
	 * @throws RuntimeException
	 */
	public static String getSha512(String value) {
		try {
			byte[] encryptedBytes = MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < encryptedBytes.length; i++) {
				sb.append(String.format("%02x", encryptedBytes[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

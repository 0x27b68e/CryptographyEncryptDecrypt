package CryptographyEncryptDecrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricKeyAlgorithm {
	
	public static void main(String[] args) {
		String string = "Hello World";
		System.out.println("Encryption using AES Algorithm: " + encrypt(string));
		System.out.println("Decryption from cipher text above: " + decrypt(encrypt(string)));
	}
	
	public static String initVector = "AAAAAAAAAAAAAAAA"; //chuổi bất kì 16 kí tự
	public static String key = "BBBBBBBBBBBBBBBB"; // chuổi bất kì 16 kí tự
	public static String encrypt(String text) {
		
		try {
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES"); // use Advanced Encrypt Standard
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			
			byte[] doFinal = cipher.doFinal(text.getBytes());
			
			return Base64.getEncoder().encodeToString(doFinal);
			
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		
		return null;
		
	}
	
	public static String decrypt(String cipherText) {
		try {
			IvParameterSpec ivParameterSpec = new IvParameterSpec(initVector.getBytes());
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(cipherText));
			
			return new String(doFinal);
			
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

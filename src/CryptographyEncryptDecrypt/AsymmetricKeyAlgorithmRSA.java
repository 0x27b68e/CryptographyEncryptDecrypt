package CryptographyEncryptDecrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//refer https://viblo.asia/p/java-ma-hoa-va-giai-ma-voi-thuat-toan-rsa-bJzKmW3Xl9N#_ma-hoa-va-giai-ma-du-lieu-trong-javarsa--cach-thuc-hoat-dong-2
public class AsymmetricKeyAlgorithmRSA {
	/*public static File publicKeyFile = new File("D://client/publickey.rsa");
	public static File privateKeyFile = new File("D://server/privatekey.rsa");*/
	
	public static void main(String[] args) {
		
		PublicKey publicKey = GenerateKeyPairUtils.getPublicKey();
		System.out.println("Public Key: " + publicKey.toString());
		PrivateKey privateKey = GenerateKeyPairUtils.getPrivateKey();
		System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.toString().getBytes()));
		
		// Now, Test
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the text to encrypt using publicKey and send to server: ");
		String text = scanner.nextLine();
		// start to encryt
		System.out.println(text + " encrypted and can send through network securely ^^: \n");
		System.out.println("Encrypted text is :" + encrypt(text));
		
		System.out.println("Server will use private key to decrypt and text will :" + decrypt(encrypt(text)));

	}
	
	public static String encrypt(String text) {
		try {		
			PublicKey generatePublic = GenerateKeyPairUtils.getPublicKey();
			//Now, we have friendly publickey, next step, I will encrypt the text using new friendly publickey.
			// Create Cipher object with parameter ARS algorithm
			Cipher cipher = Cipher.getInstance("RSA");
			//init cipher with ENCRYPT_MODE
			cipher.init(Cipher.ENCRYPT_MODE, generatePublic);
			
			//ok, done. I have encypt the text by using publickey, the text will output as below :
			//"Xcw0VLJOgJsiIi/8xj4z67ub/XlXiQtSOPV+IgQTNmJQ4uYLDuVeRf+NcIvMATNpBsUbK+IHGSu/q+gJtF
			//   r4U7fyBqeoRyTGq56Azb8dlhid64b+hHEjKEp5AmjnRsOhCXXnwlJ/juuLNTqbl8NqXwCm3k3nYCrq3CFhmkd35io="
			byte[] doFinal = cipher.doFinal(text.getBytes());
			return Base64.getEncoder().encodeToString(doFinal);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String decrypt(String encryptedText) {
		//For decryption we will be using private key 
		try {		
			PrivateKey generatePrivate = GenerateKeyPairUtils.getPrivateKey();
			//ok, now I have friendly private key, I will start to decrypt the encryptedText
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, generatePrivate);
			//Base64 encoded RSA private key for decryption.
			byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes()));
			Base64.getEncoder().encodeToString(doFinal);
			return new String(doFinal);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encryptedText;
	}

}

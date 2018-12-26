package CryptographyEncryptDecrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class GenerateKeyPairUtils {
	private static File publicKeyFile = new File("D://client/publickey.rsa");
	private static File privateKeyFile = new File("D://server/privatekey.rsa");
	
	public static GenerateKeyPairUtils getInstance() {
		return new GenerateKeyPairUtils();
	}
	
	public GenerateKeyPairUtils() {
		generateKeyPair();
	}	
		
	public static void generateKeyPair() {
		
    try {
    	   PublicKey publicKey;
    	   PrivateKey privateKey;
			
			// Create keypair using RSA algorithm, Other algorithm such as: "ECDSA"
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			// Create a secire random number generator using the SHA1PRNG algorithm
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			
			//init keypair with 1024 bit
			keyGen.initialize(1024, secureRandom);
			
			KeyPair generateKeyPair = keyGen.genKeyPair();
			 publicKey = generateKeyPair.getPublic();
			privateKey = generateKeyPair.getPrivate();
			
			if(!publicKeyFile.exists() || publicKeyFile.exists()) {
			 publicKeyFile.delete();
			 publicKeyFile.createNewFile();
			}
			if(!privateKeyFile.exists() || privateKeyFile.exists()) {
			 privateKeyFile.delete();
			 privateKeyFile.createNewFile();
			}
			
			FileOutputStream fileOutputPublicKey = new FileOutputStream(publicKeyFile);
			//write publicKey into publicKey.rsa file
			fileOutputPublicKey.write(publicKey.getEncoded());
			fileOutputPublicKey.close();
			
			FileOutputStream fileOutputPrivateKey = new FileOutputStream(privateKeyFile);
			//write privateKey into privateKey.rsa file
			fileOutputPrivateKey.write(privateKey.getEncoded());
			fileOutputPrivateKey.close();
			
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static PublicKey getPublicKey() {
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("D://client/publickey.rsa");
			byte[] byteArray = new byte[fileInputStream.available()];
			fileInputStream.read(byteArray);
			fileInputStream.close();
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteArray);
			PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);

			return generatePublic;
		} catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static PrivateKey getPrivateKey() {

		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream("D://server/privateKey.rsa");
			byte[] byteArray = new byte[fileInputStream.available()];
			fileInputStream.read(byteArray);
			fileInputStream.close();
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteArray);
			PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
			return generatePrivate;

		} catch (InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

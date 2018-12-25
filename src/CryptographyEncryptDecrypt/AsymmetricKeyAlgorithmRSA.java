package CryptographyEncryptDecrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AsymmetricKeyAlgorithmRSA {
	public static File publicKeyFile = new File("D://client/publickey.rsa");
	public static File privateKeyFile = new File("D://server/privatekey.rsa");
	
	public static void main(String[] args) {
		
		try {
			
			// Create keypair using RSA algorithm, Other algorithm such as: "ECDSA"
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			// Create a secire random number generator using the SHA1PRNG algorithm
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			
			//init keypair with 1024 bit
			keyGen.initialize(1024, secureRandom);
			
			KeyPair generateKeyPair = keyGen.genKeyPair();
			PublicKey publicKey = generateKeyPair.getPublic();
			PrivateKey privateKey = generateKeyPair.getPrivate();
			
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
			
			System.out.println("Create key pair ok!!");
			
			// Now, Test
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the text to encrypt using publicKey and send to server: ");
			String text = scanner.nextLine();
			// start to encryt
			System.out.println(text + " encrypted and can send through network securely ^^: \n" + encrypt(text));
			
			
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block;
			e.printStackTrace();
		}

	}
	
	/*public static void main(String[] args) {
		// Read publickey.rsa file 
					FileInputStream fileInputStream;
					try {
						fileInputStream = new FileInputStream("D://publickey.rsa");
						int available = fileInputStream.available();
						System.out.println(available);
						byte[] b = new byte[available];
						fileInputStream.read(b);
						System.out.println(new String(b));
						
						X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(b);
						KeyFactory keyFactory = KeyFactory.getInstance("RSA");
						PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
						String string2 = publicKey.toString();
						System.out.println(string2);
						
						Cipher cipher = Cipher.getInstance("RSA");
						//init cipher with ENCRYPT_MODE
						cipher.init(Cipher.ENCRYPT_MODE, publicKey);
						byte[] doFinal = cipher.doFinal("Hello world".getBytes());
						System.out.println("Hello world after encrypt with public key: " + Base64.getEncoder().encodeToString(doFinal));
						
						
					} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	}*/
	
	public static String encrypt(String text) {
		try {
			// Read publickey.rsa file 
			FileInputStream fileInputStream = new FileInputStream("D://client/publickey.rsa");
			//create byte array with lengh of text of publickey.rsa: ex: 162
			byte[] byteArray = new byte[fileInputStream.available()];
			// insert text of publickey.rsa into byteArray variable
			fileInputStream.read(byteArray);
			fileInputStream.close();
			
			// Because characters in publickey.rsa is difficult to read
			// ex: 
			//0Ÿ0
			//	*†H†÷
			//  0‰ €<7ÁIç1pBï“ÕÊO°™>«BSŸÞ#€&qÇªŽ¹lÝ°'!«ê]]”büê¢•ö¾®â øuÜç~±WP$ñL»#º¥Åhœ.ü
			//¼¿’ÔžjÉÆ­fßuIŒÈ^d.3JŽ¬µŒµµ˜àN“µ¡Ç8G:ƒdÊ~ç³ 
			
			// So we need to covert it for easing client/customer to read. Hence, I use X509EncodedKeySpec class to create
			// new public key
			
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteArray);
			// Create KeyFactory object and generate new publickey, okay, I have new publickey, it is friendly to read, ex: 
			//Sun RSA public key, 1024 bits
			//modulus: 90049830872911235273959796075228044346603257188619403535996907487108395899321562857131791273339565933899851985727648797494999199895037554569929682698478784994681389125308066027278503024789238566177492363123800245292349850962191848116017399107108287372881785503626287307321171170091300739177557877978264692659
			//public exponent: 65537
			PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
			
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
			
		} catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

}

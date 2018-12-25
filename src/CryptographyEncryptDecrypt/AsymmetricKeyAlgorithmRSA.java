package CryptographyEncryptDecrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.security.spec.PKCS8EncodedKeySpec;
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
			System.out.println(text + " encrypted and can send through network securely ^^: \n");
			System.out.println("Encrypted text is :" + encrypt(text));
			
			System.out.println("Server will use private key to decrypt and text will :" + decrypt(encrypt(text)));
			
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block;
			e.printStackTrace();
		}

	}
	
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
	
	public static String decrypt(String encryptedText) {
		//For decryption we will be using private key 
		try {
			FileInputStream fileInputStream = new FileInputStream("D://server/privateKey.rsa");
			byte[] byteArray = new byte[fileInputStream.available()];
			fileInputStream.read(byteArray);
			fileInputStream.close();
			
			// By default, the privateKey.rsa is generated in PKCS#8 format as below,
			//	*†H†÷
			// ‚a0‚]  ±DÖíX4óŽ7.àú{%%—(ª<&?Ž%=D1ø	Ê+¤6>
			//ÀƒÕMÙ+‚º'/þr‡°H«ÅqN›…¨ÏçÛtvñEì¶\¯{wÿÌÁ#D÷IÄ+f5À˜Ò·ÊÐÑf 'otÿýƒg•nã¨ÿ…¤Æ•ÃßF‘Å  ¦“xÿ›ñ Ão u[¼ÔŸ=Útªˆ,ÙaGa¡÷¸È	`Óêæ-÷ã86+
			//XûêD(â«·w»)ZÁNŽ6äu¿D5]héÝE¸Ý•kÓøÜÐ*Mj¯_QaÞY­ªåÐ»Žë]áNð‘Žç ÓÖ>ƒàšéÕÚŽ¨ÍN|ðeA í½1}€Ó,®×X#C(¢]øâ¥÷äÇêg™9RŒ¨ÂE¯{Êš!gb‰¯yRåÆQäw@/ÄJÏBbÑ0v÷A ¾³Æ¡(3ädìfN°J¡áåÒ
			//TîÁ4( ³¹JYÜ}jƒÁtQÑÿßÆXáÉc‘ ÖÞ<êèmžO ¢#A ¹ö•\$6$:/P~dÕGY•l'€˜sa¨V’.»˜J6”¼ÈÐB¤°4r²Zõž.é 5Ô‰gYÊ§Ö,v‘‘@Ÿÿð!„žø&hƒ0‹Ã‡Œ1?Û~Ä’¨‘ü‘ìã…_H-Ðn‡C§|Z2Xyc‹Y›’Ì¶2Gß$¨ª¥O@ 8²á"ÁÑr×ˆÉÕQs`…þšlYÞ*M¿¾1^&þÄï-E‘h´.@¿$¬`F½:£ì½€ÕºÊÑUB^‰ÏM
			
			// So before I decrypt the encryptedText, I generate the private key from base64 encoded string using PKCS8EncodedKeySpec 
			// friendly format, I will use PKCS8EncodedKeySpec class (corresponding X509EncodedKeySpec for publickey).
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteArray);
			// Using KeyFactory to create the new private key
			PrivateKey generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
			System.out.println("Private key from D://server folder is " + Base64.getEncoder().encodeToString(generatePrivate.toString().getBytes()));
			//Now, new private will have format as below:
			//c3VuLnNlY3VyaXR5LnJzYS5SU0FQcml2YXRlQ3J0S2V5SW1wbEBmZmZhMWY1ZQ==
			
			//ok, now I have friendly private key, I will start to decrypt the encryptedText
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, generatePrivate);
			//Base64 encoded RSA private key for decryption.
			byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes()));
			Base64.getEncoder().encodeToString(doFinal);
			return new String(doFinal);
			
		} catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encryptedText;
	}

}

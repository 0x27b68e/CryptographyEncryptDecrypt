package digitalSignature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Scanner;

import com.Utils.GenerateKeyPairUtils;

public class DigitalSignature {

	// A digital signature is equivalent of a handwritten signature but it is more secure, a handwritten signature can be fake.
	//A digital signature can provides the recipient the following information:
	// + the message was created by know sender. (authentication)
	// + the sender can not deny having sent the message (non-repudiation)
	// + the message was not altered in transit (intergity)
	
	// How the digital signature is created and verified
	// + Alice has a bitcoin amount and want to create a digital signature proofing to anyone that she is the owner of that bitcoin amount.
	// + Alice creates a digital signature
	//    ++ the first she hash the data (bitcoin amount, public key, recipient public key, ...)
	// Bob wants Alice bitcoin amount. Alice send the bitcoin amount and the digital signature.
	// Bob verifies the digital signature. 
	//    + Bob decrypts the digital signature using Alice public key. The result is hash value of the bitcoin amount (Hash A)
	//    + Bob applies the same hash algorithm on the received hash value(Hash A). The result is hash value of Hash A (Hash B).
	// Bob compares two hash values (Hash A and Hash B)
	// If the hash values math it proofs that the documents was not altered during transit and that the document is owned by Alice.
	
	// to create the digital signature
	// + data [using Hash function]   => hash value(00000a625119eda068490e394803b6ba16865fe4ce4253758c60058e190aab5c) + privateKey (using encrypt function) => digital signature
	// to verities a digital signature
	// digital signature + publicKey (using decrypt function) => Hash A (00000a625119eda068490e394803b6ba16865fe4ce4253758c60058e190aab5c)
	// data [using Hash function] => Hash B (00000a625119eda068490e394803b6ba16865fe4ce4253758c60058e190aab5c)
	// Alice creating a digital signature: ENC(H(p), privateKey(Alice)) = sign
	// Bob verifying a digital signature: DEC (publicKey(Alice), sign) = hash Value.
	// H(p) == hash Value. done
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		//get key from Database
		PublicKey publicKey = GenerateKeyPairUtils.getPublicKey();
		System.out.println("Public Key: " + publicKey.toString());
		PrivateKey privateKey = GenerateKeyPairUtils.getPrivateKey();
		System.out.println("Private Key: " + Base64.getEncoder().encodeToString(privateKey.toString().getBytes()));
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter data to sign:");
		String string = scanner.nextLine();
		
		byte[] createDigitalSignature = createDigitalSignature(string, privateKey);
		System.out.println("a Digital signature: " + Base64.getEncoder().encodeToString(createDigitalSignature));
		
		
		boolean b = checkDigitalSignature(publicKey, createDigitalSignature, string);
		System.out.println("Check signature: " + b);
		
	}
	
	public static byte[] createDigitalSignature(String text, PrivateKey privateKey) {
		
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(privateKey);
			signature.update(text.getBytes());
			byte[] sign = signature.sign();
			
			return sign;
		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean checkDigitalSignature(PublicKey publicKey, byte[] digitalSignature, String text) {
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initVerify(publicKey);
			signature.update(text.getBytes());
			return signature.verify(digitalSignature);
			
		} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}

}

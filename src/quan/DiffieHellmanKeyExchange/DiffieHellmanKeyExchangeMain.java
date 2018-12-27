package quan.DiffieHellmanKeyExchange;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

//refer: http://www.java2s.com/Tutorial/Java/0490__Security/DiffieHellmanKeyAgreement.htm
public class DiffieHellmanKeyExchangeMain {
	public static BigInteger P = new BigInteger("fffffffffffffffffffffffffffffffeffffffffffffffff", 16);
	public static BigInteger G = new BigInteger("fffffffffffffffffffffffffffffffefffffffffffffffc", 16);
	

	public static void main(String[] args) {
		try {

			Security.addProvider(new BouncyCastleProvider());
			
			DHParameterSpec dhParameterSpec = new DHParameterSpec(P, G);
			
			
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH", "BC");
			SecureRandom secureRandom = new SecureRandom();
			keyPairGenerator.initialize(dhParameterSpec, secureRandom);
			
			
			KeyAgreement akeyAgreement = KeyAgreement.getInstance("DH", "BC");
			KeyPair akeyPair = keyPairGenerator.genKeyPair();
			akeyAgreement.init(akeyPair.getPrivate());
			
			KeyAgreement bkeyAgreement = KeyAgreement.getInstance("DH", "BC");
			KeyPair bkeyPair = keyPairGenerator.genKeyPair();
			bkeyAgreement.init(bkeyPair.getPrivate());
			
			akeyAgreement.doPhase(bkeyPair.getPublic(), true);
			bkeyAgreement.doPhase(akeyPair.getPublic(), true);
			
			byte[] agenerateSecret = akeyAgreement.generateSecret();
			byte[] bgenerateSecret = akeyAgreement.generateSecret();
			boolean b = new String(agenerateSecret).equals(new String(bgenerateSecret));
			System.out.println("Compare: " + b);
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
}
}

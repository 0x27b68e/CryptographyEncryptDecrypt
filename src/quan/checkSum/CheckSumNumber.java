package quan.checkSum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//Some identification number such as bank account numbers have some digits embedded in thse numbers.
//These digitals which can be numbers or characters, are called "checksum digits" and are used for error detection when you mistype the identification number
// For example: This is a Dutch bank account number NL91 ABNA 0417 1643 00. In this example the 2 digit checksum value is "91". A spcial algorithm is applied to 
// this blank account number to calcualate this checksum value. If you mistype this bank account number NL91 ABNA 0417 1463 00, any Dutch operation will notice this error 
// because the checksum value of this number does not corresponds to the expected checksum digit "91"

// CryptoCurrency addresses such as Bitcoin are also using checksum digits.
// I m not sure if all blockchain implementations are using checksum digits in their address.

// * Checksum vs Hash
//    ** A checksum generally designed to detect accidental errors in small blocks of data (such as Social Security numbers, bank account numbers, cryptocurrency address etc)
// and  often to be fast to compute.
//    ** A hash reduces large data a small number, in a way that minimizes the change of collisions
public class CheckSumNumber {
	//refer: https://www.mkyong.com/java/how-to-generate-a-file-checksum-value-in-java/
	public static void main(String[] args) {
		File file = new File("D://checksum/text.txt");

		try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				System.out.println("File is exist!");
			}
			String string = checkSum(file);
			System.out.println("Hash of file: " + string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String checkSum(File file) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-384"); //change the algorithm SHA, MD2, MD5, SHA-256, SHA-384...
			
			FileInputStream fileInputStream =  new FileInputStream(file);
			DigestInputStream digestInputStream = new DigestInputStream(fileInputStream,  md);
			while (digestInputStream.read() != -1) {
				md = digestInputStream.getMessageDigest();
			}
			
			byte[] digest = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < digest.length; i++) {
				String hex = Integer.toHexString(0xff & digest[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
			
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
		}
		return  null;
	}

}

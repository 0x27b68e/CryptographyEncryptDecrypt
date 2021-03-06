package com.quan;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.digest.DigestUtils;


//a Hash function converts data of any size into a string of fiexed size, (32 character)
//a small change in the data will result in a complete different hash.
// A hash function is considered  "collision resistant" when it is very hard to find two inputs to the same output
// There are many different hash function here is a few: MD5 (message digest), SHA256 (Secure Hash algorithm)
public class Main {
	static Integer autoIncreate = 0;

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String string = "Hello";
		String md5 = DigestUtils.md5Hex(string);
		
		//System.out.println(md5.toString());
		
		
		//7a6d1b13498fb5b3085b2fd887933575
		//b83099b8ce596f31f2f60c8fd4d72826

		while(!testMD5().startsWith("00")) {
			autoIncreate++;
			System.out.println(calculateHash());
		}
		
	}
	
	
	public static String calculateHash() {
		String valueOf = String.valueOf(autoIncreate.hashCode());
		String md5 = DigestUtils.md5Hex(valueOf);
		return  md5;
	}
	
	public static String testMD5() throws NoSuchAlgorithmException{
		String valueOf = String.valueOf(autoIncreate.hashCode());
		
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(valueOf.getBytes());
	    byte[] digest = md.digest();
	    
	    String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash;
	}

}

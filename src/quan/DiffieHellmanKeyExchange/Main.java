package quan.DiffieHellmanKeyExchange;


//Diffie-Hellman key exchange is a  method to securely establish a shared secret between two parties (Alice and Bob) over a public channel
// Alice and Bob agrees on the publicly shared domain parameter @(generator) and p (modulus). For example: @ =3, p = 17
public class Main {

	//refer: https://www.geeksforgeeks.org/implementation-diffie-hellman-algorithm/
	public static void main(String[] args) {
		// Step 1: Alice and Bob get public number  P = 23, G = 9
		long P = 23;
		long G = 15;
		System.out.println("P: " + P +" G: " + G);
		
		// Alice will choose the private key a = 4,
		long a = 4;
		System.out.println("Private Key a = " + a);
		//get the generated key
		long x = power(G, a, P);
		
	// Bob will choose the private key b = 3
		long b =3;
		System.out.println("Private key b = " +b);
		long y = power(G, b, P);
		
		// Generating the secret key after exchange of the keys
		long secretKeya = power(y, a, P); // Secret key of Alice
		long secretKeyb = power(x, b, P); // Secret key of Bob
		System.out.println("Secret key of  Alice is: " + secretKeya);
		System.out.println("Secret key of  Bob is: " + secretKeyb);
		
		
		
		
	}
	
	//
	public static long  power(long  a, long b, long P) {
		if(b == 1) {
			return a;
		} else {
			return (long) (Math.pow(a, b) % P);
		}
	}

}

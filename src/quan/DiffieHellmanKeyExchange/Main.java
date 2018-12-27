package quan.DiffieHellmanKeyExchange;


/*Step 1: Alice and Bob get public numbers P = 23, G = 9

Step 2: Alice selected a private key a = 4 and
        Bob selected a private key b = 3

Step 3: Alice and Bob compute public values
Alice:    x =(9^4 mod 23) = (6561 mod 23) = 6
        Bob:    y = (9^3 mod 23) = (729 mod 23)  = 16

Step 4: Alice and Bob exchange public numbers

Step 5: Alice receives public key y =16 and
        Bob receives public key x = 6

Step 6: Alice and Bob compute symmetric keys
        Alice:  ka = y^a mod p = 65536 mod 23 = 9
        Bob:    kb = x^b mod p = 216 mod 23 = 9

Step 7: 9 is the shared secret.*/

public class Main {

	//refer: https://www.geeksforgeeks.org/implementation-diffie-hellman-algorithm/
	public static void main(String[] args) {
		// Step 1: Alice and Bob get public number  P = 23, G = 15
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
	public static long  power(long  a, long b, long P) {
		if(b == 1) {
			return a;
		} else {
			return (long) (Math.pow(a, b) % P);
		}
	}

}

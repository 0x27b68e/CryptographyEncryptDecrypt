package CryptographyEncryptDecrypt;

//Cryptography converts data into a particular form so that only those for whom it is intended can read and process it. The converted data is however 
// unreadbale for unauthorized user.
// In cryptography, the fictional characters as Alice, Bob, Carol, Dave, and Eve are used to make it easier for people to understand certain cryptography implementations
// ex: "Hello World"   (encryption)  ===> "#@w3twT" (encrypted text/ cipher text) :Encryption converts a readable message into a unreadable message.
//ex: "#@w3twT"  (decryption)  ===> "Hello World" (plain text)                     ::Decryption converts a unreadable message into a readable message.
//The character Eve is often used to represent an aevesdropper

// there are two basic key algorithm Symmetric and Asymmetric
// + Symmetric: A symmetric required the same key for both encrypt of a  plaintext and decrypt of a cipher text.
// + This same key is also called a shared secret.
// + Symmetric key algorithms are generally much faster (hundreds of thousands times) to encrypt and decrypt a message than asymmetric key algorithm.
// + Disadvantage of using a symmetric key algorithm is that both sender (Alice) and reciever (Bob) needs to know the shared secret key.
// + There are few symmetric key algorithms: AES (Advanced Encryption Standard), Triple DES (Data Encryption Standard) 

// Asymmetric:
// + An Asymmetric key algorithm require two keys called a public and private key. One of the key is used for encryption of a plaintext and  other key is used 
// for decryption of the cipher text.
// If Alice generates a private key and corresponding public key, than anyone is allowed to know her public key, but Alice must keep her private key secret.
// + Big disadvantage is that asymmetric key algorithm are generally much lower (hundreds of thousands times) to encrypt and decrypt a message than symmetric key algorithm.
// + The advantages of asymmetric key algorithm is that any sender can encrypt a message using the receive public key but only the receiver can decrypt the cipher message
// using its private key.

// A public and private key are mathematically interconnected. Meaning each public key have only one corresponding a private key.
// In Blockchain ECDSA is often used.
//ex:  Encrypt: "Hello World" + (public key) ====> "AFD53FVD65DCCDDG234DFF" (Cipher text). The public key is used for encryption of a plain text and the private key for decryption
// of the cipher text.
//  Decryption: "AFD53FVD65DCCDDG234DFF" + private key ======> "Hello World".
// OR  Encypt: "Hello World" + (private key) ====> "AFD53FVD65DCCDDG234DFF" (Cipher text). The private key is used for encrytin of a  plain text and the private key for
//decrytion of the ciphe text.
//  Decrytion:    "AFD53FVD65DCCDDG234DFF" + public key ======> "Hello World".


public class Main {
	
	
	

}

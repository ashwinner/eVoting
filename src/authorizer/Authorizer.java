package authorizer;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class Authorizer {

	private static Authorizer instance = null;
	private RSAPrivateKey privateKey;
	public RSAPublicKey publicKey;
	
	private Authorizer() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(512, SecureRandom.getInstance("SHA1PRNG"));
			
			KeyPair pair = keyGen.generateKeyPair();
			privateKey = (RSAPrivateKey) pair.getPrivate();
			publicKey = (RSAPublicKey) pair.getPublic();
			
		} catch (NoSuchAlgorithmException ex) {}
		
	}
	
	public static Authorizer getInstance() {
		
		if(instance==null)
			instance = new Authorizer();
		
		return instance;
		
	}
	
	public BigInteger sign(BigInteger generatedId) {
		
		BigInteger d = privateKey.getPrivateExponent();
		BigInteger n = privateKey.getModulus();
		return generatedId.modPow(d, n);
		
	}
	
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

}
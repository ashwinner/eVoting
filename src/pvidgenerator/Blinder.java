package pvidgenerator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import authorizer.Authorizer;

public class Blinder {
	
	BigInteger blindingFactor;
	Authorizer authorizer;
	
	
	public Blinder(Authorizer authorizer) {
		
		this.authorizer = authorizer;
		blindingFactor = generateBlindingFactor();
		
	}
	
	public BigInteger blind(BigInteger numberToBlind) {
		
		RSAPublicKey authorizerPublicKey = authorizer.getPublicKey();
		BigInteger e = authorizerPublicKey.getPublicExponent();
		BigInteger n = authorizerPublicKey.getModulus();
		return blindingFactor.modPow(e, n).multiply(numberToBlind).mod(n);
		
	}
	
	public BigInteger unblind(BigInteger numberToUnblind) {
		
		RSAPublicKey authorizerPublicKey = authorizer.getPublicKey();
		BigInteger n = authorizerPublicKey.getModulus();
		return blindingFactor.modInverse(n).multiply(numberToUnblind).mod(n);
		
	}
	
	private BigInteger generateBlindingFactor() {
		
		RSAPublicKey authorizerPublicKey = authorizer.getPublicKey();
		BigInteger n = authorizerPublicKey.getModulus();
		BigInteger generatedNumber;
		do {
			generatedNumber = new BigInteger(511, new SecureRandom());
		} while(!generatedNumber.gcd(n).equals(BigInteger.ONE));
			
		return generatedNumber;
	}

}
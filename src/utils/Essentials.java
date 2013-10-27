package utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;

import authorizer.Authorizer;

public class Essentials {
	
	public static boolean isValidPVID(String PVID) {
		// TODO Auto-generated method stub
		Authorizer authorizer=Authorizer.getInstance();
		BigInteger pvid= new BigInteger(PVID);
		RSAPublicKey authorizerPublicKey=authorizer.getPublicKey();
		BigInteger e= authorizerPublicKey.getPublicExponent();
		BigInteger n= authorizerPublicKey.getModulus();
		BigInteger signVerifier= pvid.modPow(e, n);
		if(signVerifier.toString().startsWith("1000"))
			return true;
		else
			return false;
	}
	
	public static String hashOf(String encryptedVote) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		MessageDigest msgDigest=MessageDigest.getInstance("MD5");
		msgDigest.update(encryptedVote.getBytes(), 0, encryptedVote.length());
		BigInteger hash= new BigInteger(1, msgDigest.digest());
		return hash.toString(16);
	}
}

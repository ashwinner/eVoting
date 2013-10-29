package keygenerator;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import utils.Essentials;


import exceptions.InvalidPvidException;

import authorizer.Authorizer;

public class keyGenerator {
	
	private static keyGenerator instance = null;
	
	private Authorizer authorizer;
	private javax.crypto.KeyGenerator keyGen;
	private Map<String, Key> pvidToKeyMap;
	
	private keyGenerator() throws NoSuchAlgorithmException {
		
		this.authorizer = authorizer.getInstance();
		keyGen = javax.crypto.KeyGenerator.getInstance("AES");
		keyGen.init(128, new SecureRandom());
		pvidToKeyMap = new HashMap<String, Key>();
		
	}
	
	public Key generateKey(String pvid) throws InvalidPvidException {
		
		if(!Essentials.isValidPVID(pvid))
			throw new InvalidPvidException();
		
		Key key = keyGen.generateKey();
		pvidToKeyMap.put(pvid, key);
		
		return key;
	}
	
	
}

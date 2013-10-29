package keygenerator;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import utils.Essentials;


import exceptions.InvalidPvidException;

import authorizer.Authorizer;

public class KeyGenerator {
	
	private static KeyGenerator instance = null;
	
	private javax.crypto.KeyGenerator keyGen;
	private Map<String, Key> pvidToKeyMap;
	
	private KeyGenerator() throws NoSuchAlgorithmException {
		
		keyGen = javax.crypto.KeyGenerator.getInstance("AES");
		keyGen.init(128, new SecureRandom());
		pvidToKeyMap = new HashMap<String, Key>();
		
	}
	
	public static KeyGenerator getInstance() throws NoSuchAlgorithmException {
		if(instance == null)
			return new KeyGenerator();
		
		return instance;
		
	}
	
	public Key generateKey(String pvid) throws InvalidPvidException {
		
		if(!Essentials.isValidPVID(pvid))
			throw new InvalidPvidException();
		
		Key key = keyGen.generateKey();
		pvidToKeyMap.put(pvid, key);
		
		return key;
	}
	
	public Map<String, Key> getPvidToKeyMap() {
		return pvidToKeyMap;
	}
	
	
}

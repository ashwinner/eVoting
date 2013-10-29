package pvidgenerator;

import authorizer.Authorizer;

import java.math.BigInteger;
import java.util.*;

import exceptions.InvalidPinException;

public class PVIDGenerator {

	private static PVIDGenerator instance = null;
	
	Authorizer authorizer;
	Map<String, String> emailIdToPasskeyMap;
	
	public static PVIDGenerator getInstance(Map<String, String> emailIdToPasskeyMap) {
		
		if(instance == null) 
			instance = new PVIDGenerator(emailIdToPasskeyMap);
		
		return instance;
	}
	
	private PVIDGenerator(Map<String, String> emailIdToPasskeyMap) {
		this.authorizer=Authorizer.getInstance();
		this.emailIdToPasskeyMap=emailIdToPasskeyMap;
	}
	
	public String generatePVID(String emailId, String pin) throws IllegalArgumentException, InvalidPinException{
		
		if(!emailIdToPasskeyMap.containsKey(emailId)) {
			throw new IllegalArgumentException("emailId " + emailId + " doesnt exist");
		}
		
		if(!emailIdToPasskeyMap.get(emailId).equals(pin))
			throw new InvalidPinException();
			
		Blinder blinder = new Blinder(authorizer);
		BigInteger generatedId = new BigInteger("1000" + new BigInteger(32, new Random()).toString());
		BigInteger blindedId = blinder.blind(generatedId);
		BigInteger signedId = authorizer.sign(blindedId);
		BigInteger pvid = blinder.unblind(signedId);		
		return pvid.toString();
	}

}
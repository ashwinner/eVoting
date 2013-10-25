package pvidgenerator;

import authorizer.Authorizer;
import exceptions.InvalidPinException;

import java.math.BigInteger;
import java.util.*;

public class PVIDGenerator {

	private static PVIDGenerator instance = null;
	
	Authorizer authorizer;
	Map<String, String> emailIdToPasskeyMap;
	
	public static PVIDGenerator getInstance(Authorizer authorizer, Map<String, String> emailIdToPasskeyMap) {
		
		if(instance == null) 
			instance = new PVIDGenerator(authorizer, emailIdToPasskeyMap);
		
		return instance;
	}
	
	private PVIDGenerator(Authorizer authorizer, Map<String, String> emailIdToPasskeyMap) {
		this.authorizer=authorizer;
		this.emailIdToPasskeyMap=emailIdToPasskeyMap;
	}
	
	public String generatePVID(String emailId, String PIN) throws InvalidPinException, IllegalArgumentException{
		
		if(!emailIdToPasskeyMap.containsKey(emailId)) {
			throw new IllegalArgumentException("emailId " + emailId + " doesnt exist");
		}
		
		if(!emailIdToPasskeyMap.get(emailId).equals(PIN)) {
			throw new InvalidPinException();
		}
			
		Blinder blinder = new Blinder(authorizer);
		BigInteger generatedId = new BigInteger("1000" + new BigInteger(32, new Random()).toString());
		BigInteger blindedId = blinder.blind(generatedId);
		BigInteger signedId = authorizer.sign(blindedId);
		BigInteger pvid = blinder.unblind(signedId);		
		return pvid.toString();
	}

}
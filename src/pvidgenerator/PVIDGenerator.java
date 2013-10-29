package pvidgenerator;

import authorizer.Authorizer;

import java.math.BigInteger;
import java.util.*;

import exceptions.InvalidPinException;
import exceptions.InvalidPvidException;

public class PVIDGenerator {

	private static PVIDGenerator instance = null;
	
	Authorizer authorizer;
	Map<String, String> emailIdToPasskeyMap;
	
	List<String> pvidGeneratedEmails;
	
	public static PVIDGenerator getInstance(Map<String, String> emailIdToPasskeyMap) {
		
		if(instance == null) 
			instance = new PVIDGenerator(emailIdToPasskeyMap);
		
		return instance;
	}
	
	private PVIDGenerator(Map<String, String> emailIdToPasskeyMap) {
		
		this.authorizer=Authorizer.getInstance();
		this.emailIdToPasskeyMap=emailIdToPasskeyMap;
		this.pvidGeneratedEmails = new ArrayList<String>();
	}
	
	public String generatePVID(String emailId, String pin) throws IllegalArgumentException, InvalidPinException{
		
		if(!emailIdToPasskeyMap.containsKey(emailId)) {
			throw new IllegalArgumentException("emailId " + emailId + " doesnt exist");
		}
		
		if(!emailIdToPasskeyMap.get(emailId).equals(pin))
			throw new InvalidPinException();
			
		if(pvidGeneratedEmails.contains(emailId))
			throw new IllegalArgumentException("Email id already has associated PVID");
		
		Blinder blinder = new Blinder(authorizer);
		BigInteger generatedId = new BigInteger("1000" + new BigInteger(32, new Random()).toString());
		BigInteger blindedId = blinder.blind(generatedId);
		BigInteger signedId = authorizer.sign(blindedId);
		BigInteger pvid = blinder.unblind(signedId);
		
		pvidGeneratedEmails.add(emailId);
		return pvid.toString();
	}

}
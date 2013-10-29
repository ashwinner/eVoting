package voter;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import keygenerator.KeyGenerator;

import pvidgenerator.PVIDGenerator;
import utils.Essentials;
import collector.Collector;
import collector.CollectorBullettinBoard;
import exceptions.InvalidPinException;
import exceptions.InvalidPvidException;
import ballotgenerator.BallotGenerator;

public class Voter {
	
	private PVIDGenerator pvidGenerator;
	private BallotGenerator ballotGenerator;
	private Collector collector;
	private CollectorBullettinBoard collectorBullettinBoard;
	private KeyGenerator keyGenerator;
	
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public Voter(PVIDGenerator pvidGenerator, BallotGenerator ballotGenerator,Collector collector) throws NoSuchAlgorithmException{
		
		this.pvidGenerator =  pvidGenerator;
		this.ballotGenerator=ballotGenerator;
		this.collector=collector;
		this.collectorBullettinBoard=CollectorBullettinBoard.getInstance();
		this.keyGenerator = KeyGenerator.getInstance();
	}
	
	public void run() throws IOException, NoSuchAlgorithmException, InvalidPvidException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
	{
		try{
			
		//Displaying choices that the voter has
		Voter.displayMainMenu();
		System.out.println("Please enter your choice");
		
		int choice = Integer.parseInt(reader.readLine());
		
	switch(choice) {
	
	case 1 ://generate PVID
		System.out.println("Generate PVID option selected");
		System.out.println("Enter your nitc email ID ");
		String email_id = reader.readLine();
		System.out.println("Enter your pin/passkey");
		String pin = reader.readLine();
		
		String PVID = pvidGenerator.generatePVID(email_id, pin);
		System.out.println("Your PVID for the voting process is : " + PVID);
		break;
		
	case 2 ://Vote
		System.out.println("VOTE option selected");
		System.out.println("Enter your PVID");
		String PVID1 = reader.readLine(); 
		List<String> ballot=ballotGenerator.requestBallot(PVID1);
		this.displayBallot(ballot);
		System.out.println("Enter your Vote");
		int vote= Integer.parseInt(reader.readLine());
		String encryptedVote = this.encryptVote(PVID1, vote);
		System.out.println(encryptedVote);
		collector.recordVote(PVID1,encryptedVote);
		break;
		
	case 3 ://Verify
		System.out.println("Verify option selected");
		System.out.println("Enter PVID");
		String PVID11 = reader.readLine();
		System.out.println("Enter Encrypted Vote");
		String encryptedVoteToVerify = reader.readLine();
		if(collectorBullettinBoard.verify(PVID11, Essentials.hashOf(encryptedVoteToVerify)))
			System.out.println("Your Vote has been recorded correctly");
		else
			System.out.println("Your Vote is not recorded correctly");
		
		break;
	default : //Invalid input
		System.out.println("Invalid Input");
	}
	
	} catch (IllegalArgumentException ex) {
		System.out.println(ex.getMessage());
	
	} catch (InvalidPinException ex) {
		System.out.println("The PIN you entered is invalid");
	}
	

}
	private String encryptVote(String pvid, int vote) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidPvidException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keyGenerator.generateKey(pvid));
		byte[] encryptedVote = cipher.doFinal(new Integer(vote).toString().getBytes());
		
		return new String(encryptedVote);
	}

	private void displayBallot(List<String> ballot) {
		
		int slNo = 0;
		for (String candidate : ballot)
			System.out.println(++slNo + " " + candidate);
		
	}
	
	private static void displayMainMenu() {
		
		System.out.println("Welcome to the E-Voting system");
		System.out.println("*******************************");
		System.out.println("1.Generate PVID");
		System.out.println("2.Vote");
		System.out.println("3.Verify");
		
	}
}

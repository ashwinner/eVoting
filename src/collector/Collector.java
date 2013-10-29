package collector;


import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import utils.Essentials;

public class Collector {
	private static Collector instance=null;
	private CollectorBullettinBoard collectorBullettinBoard;
	private static Map<String, byte[]> pvidToEncryptedVoteMap;
	
	public static Collector getInstance()
	{
		if(instance==null){
			instance= new Collector(); 
		}
		return instance;
		
	}
	
	private Collector() {
		pvidToEncryptedVoteMap = new HashMap<String, byte[]>();
		collectorBullettinBoard = CollectorBullettinBoard.getInstance();
		
	}
	public void recordVote(String PVID, byte[] encryptedVote) throws NoSuchAlgorithmException
	{
		pvidToEncryptedVoteMap.put(PVID,encryptedVote);
		collectorBullettinBoard.addEntry(PVID, Essentials.hashOf(encryptedVote));
	}
	public Map<String,byte[]> getPVIDtoEncryptedVoteMap(){
		return pvidToEncryptedVoteMap;
	}
}

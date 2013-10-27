package collector;


import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import utils.Essentials;

public class Collector {
	private static Collector instance=null;
	private CollectorBullettinBoard collectorBullettinBoard;
	private static Map<String, String> pvidToVoteMap;
	
	public static Collector getInstance()
	{
		if(instance==null){
			instance= new Collector(); 
		}
		return instance;
		
	}
	
	private Collector() {
		pvidToVoteMap=new HashMap<String, String>();
		collectorBullettinBoard= CollectorBullettinBoard.getInstance();
		
	}
	public void recordVote(String PVID, String encryptedVote) throws NoSuchAlgorithmException
	{
		pvidToVoteMap.put(PVID,encryptedVote);
		collectorBullettinBoard.addEntry(PVID, Essentials.hashOf(encryptedVote));
	}
	public Map<String,String> getPVIDtoVoteMap(){
		return pvidToVoteMap;
	}
}

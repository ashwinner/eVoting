package collector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CollectorBullettinBoard {
	private static CollectorBullettinBoard instance=null;
	private static Map<String,byte[]> collectorBullettinBoard;
	public static CollectorBullettinBoard getInstance()
	{
		if(instance==null){
			instance= new CollectorBullettinBoard();
		}
		return instance;
		
	}
	
	private CollectorBullettinBoard(){
		collectorBullettinBoard= new HashMap<String,byte[]>();
	}
	
	public void addEntry(String PVID,byte[] hashOfEncryptedVote){
		collectorBullettinBoard.put(PVID, hashOfEncryptedVote);
	}
	
	public boolean verify(String PVID, byte[] hashOfEncryptedVote){
		
		if(Arrays.equals(collectorBullettinBoard.get(PVID), hashOfEncryptedVote))
			return true;
		else
			return false;
	}
	
	public Map<String,byte[]> getCollectorBullettinBoard(){
		return collectorBullettinBoard;
		
	}
	

}

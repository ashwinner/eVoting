package collector;

import java.util.HashMap;
import java.util.Map;

public class CollectorBullettinBoard {
	private static CollectorBullettinBoard instance=null;
	private static Map<String,String> collectorBullettinBoard;
	public static CollectorBullettinBoard getInstance()
	{
		if(instance==null){
			instance= new CollectorBullettinBoard();
		}
		return instance;
		
	}
	private CollectorBullettinBoard(){
		collectorBullettinBoard= new HashMap<String,String>();
	}
	public void addEntry(String PVID,String hashOfEncryptedVote){
		collectorBullettinBoard.put(PVID, hashOfEncryptedVote);
	}
	public boolean verify(String PVID, String hashOfEncryptedVote){
		if(collectorBullettinBoard.get(PVID).equals(hashOfEncryptedVote))
			return true;
		else
			return false;
	}
	public Map<String,String> getCollectorBullettinBoard(){
		return collectorBullettinBoard;
		
	}
	

}

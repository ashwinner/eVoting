package collector;


import java.util.HashMap;
import java.util.Map;

public class Collector {
	private static Collector instance=null;
	private static Map<String, Integer> m=new HashMap<String, Integer>();
	public static Collector getInstance()
	{
		if(instance==null){
			instance= new Collector(); 
		}
		return instance;
		
	}
	private Collector() {
	}
	public void recordVote(String PVID, int vote)
	{
		m.put(PVID,vote);
	}
	public Map<String,Integer> getPVIDtoVoteMap(){
		return m;
	}
}

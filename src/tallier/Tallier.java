package tallier;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Tallier {
	
	private static Tallier instance=null;
	
	public static Tallier getInstance() {
		
		if(instance==null){
			instance=new Tallier();
		}
		
		return instance;		
	}
	
	public Map<String, Integer> tally(Map<String,Integer> pvidToVoteMap, List<String> candidateList ){
		
		Map<String,Integer> tallyMap;
		tallyMap=initializeTallyMap(candidateList);
		
		Iterator<Entry<String, Integer>> entries = pvidToVoteMap.entrySet().iterator();
		while (entries.hasNext()) {
		  Entry<String, Integer> thisEntry = (Entry<String, Integer>) entries.next();
		 // String key = (String) thisEntry.getKey();
		  int value = thisEntry.getValue();
		  String candidate=candidateList.get(value-1);
		  int count=tallyMap.get(candidate);
		  tallyMap.put(candidate, ++count);	
		}
		
		return tallyMap;
	}

	
	private Map<String, Integer> initializeTallyMap (List<String> candidateList) {
		
		Map<String,Integer> tallyMap=new TreeMap<String,Integer>();
		
		for (String candidate : candidateList)
			tallyMap.put(candidate, 0);
	
		return tallyMap;
	}

}
